package com.thexeler.trade.controller.game;

import com.thexeler.trade.entity.ContractStub;
import com.thexeler.trade.entity.User;
import com.thexeler.trade.kits.TokenData;
import com.thexeler.trade.kits.TokenKits;
import com.thexeler.trade.kits.enums.MaterialType;
import com.thexeler.trade.repository.ContractStubRepository;
import com.thexeler.trade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@RestController
@RequestMapping("/api/game/contract")
@CrossOrigin
public class ContractController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContractStubRepository contractStubRepository;

    @GetMapping("/getActiveContracts")
    public Map<String, Object> getPlayerOrders(@RequestParam String token) {
        TokenData data = new TokenData(token);
        if (TokenKits.verifyToken(data)) {
            return Map.of("message", "Invalid token");
        }

        Optional<User> userOptional = userRepository.findByUsername(data.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of("message", "Invalid username");
        }

        List<Map<String, Object>> orders = contractStubRepository.findAll()
                .stream().map(contract -> {
                    Map<String, Object> order = new HashMap<>();
                    order.put("id", contract.getId());
                    order.put("expiry", contract.getExpiry().toString().substring(0, 10));
                    order.put("materialType", contract.getMaterialType() != null ? contract.getMaterialType().name() : "");
                    order.put("quantity", contract.getQuantity());
                    order.put("amount", contract.getAmount());
                    order.put("buyer", contract.getBuyer());
                    order.put("seller", contract.getSeller());
                    order.put("isActive", contract.getExpiry().isAfter(LocalDateTime.now()));
                    return order;
                }).filter(order -> order.get("isActive").equals(true)).toList();

        return Map.of("message", "Success", "data", orders);
    }

    @PostMapping("/submitOffer")
    public Map<String, Object> submitOffer(@RequestBody Map<String, Object> request) {
        String token = (String) request.get("token");
        Long orderId = ((Number) request.get("orderId")).longValue();
        String type = (String) request.get("type");
        Double amount = (Double) request.get("amount");

        TokenData data = new TokenData(token);
        if (TokenKits.verifyToken(data)) {
            return Map.of("message", "Invalid token");
        }

        Optional<User> userOptional = userRepository.findByUsername(data.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of("message", "Invalid username");
        }

        Optional<ContractStub> contractOptional = contractStubRepository.findById(orderId);
        if (contractOptional.isEmpty()) {
            return Map.of("message", "Contract not found");
        }

        ContractStub targetContract = contractOptional.get();

        if (targetContract.getExpiry().isBefore(LocalDateTime.now())) {
            return Map.of("message", "Contract has expired and cannot accept new offers");
        }

        if ("sell".equals(type) && amount < targetContract.getAmount()) {
            targetContract.setAmount(amount);
            targetContract.setExpiry(LocalDateTime.now().plusMinutes(30));
            contractStubRepository.save(targetContract);
            return Map.of("message", "Success", "info", "New lower sell offer accepted,公示 period extended");
        } else if ("buy".equals(type) && amount > targetContract.getAmount()) {
            targetContract.setAmount(amount);
            targetContract.setExpiry(LocalDateTime.now().plusMinutes(30));
            contractStubRepository.save(targetContract);
            return Map.of("message", "Success", "info", "New higher buy offer accepted,公示 period extended");
        } else {
            return Map.of("message", "Offer rejected", "info", "Price not better than current offer");
        }
    }

    @PostMapping("/createContract")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> request) {
        String token = (String) request.get("token");
        String materialTypeStr = (String) request.get("materialType");
        Integer quantity = (Integer) request.get("quantity");
        Double amount = (Double) request.get("amount");
        String buyer = (String) request.get("buyer");
        String seller = (String) request.get("seller");
        String expiryStr = (String) request.get("expiry");

        TokenData data = new TokenData(token);
        if (TokenKits.verifyToken(data)) {
            return Map.of("message", "Invalid token");
        }

        Optional<User> userOptional = userRepository.findByUsername(data.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of("message", "Invalid username");
        }

        MaterialType materialType;
        try {
            materialType = MaterialType.valueOf(materialTypeStr);
        } catch (Exception e) {
            return Map.of("message", "Invalid material type");
        }

        ContractStub newContract = new ContractStub();
        newContract.setMaterialType(materialType);
        newContract.setQuantity(quantity);
        newContract.setAmount(amount);
        newContract.setBuyer(buyer);
        newContract.setSeller(seller);
        newContract.setExpiry(LocalDateTime.parse(expiryStr + "T00:00:00")); // 解析日期
        newContract.setCreatedAt(LocalDateTime.now());

        contractStubRepository.save(newContract);

        return Map.of("message", "Success", "orderId", newContract.getId());
    }
}
