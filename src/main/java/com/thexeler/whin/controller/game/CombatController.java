package com.thexeler.whin.controller.game;

import com.thexeler.whin.dto.combat.CombatOperateRequest;
import com.thexeler.whin.entity.rules.system.Combat;
import com.thexeler.whin.entity.userdata.User;
import com.thexeler.whin.kits.TokenData;
import com.thexeler.whin.kits.TokenKits;
import com.thexeler.whin.repository.CombatRepository;
import com.thexeler.whin.repository.UserRepository;
import com.thexeler.whin.service.CombatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/game/combat")
@CrossOrigin
public class CombatController {

    @Autowired
    private CombatRepository combatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CombatService combatService;

    @PostMapping("/operate")
    public Map<String, Object> operate(@RequestBody CombatOperateRequest request) {
        TokenData tokenData = new TokenData(request.getToken());
        if (TokenKits.verifyToken(tokenData)) {
            return Map.of("success", false, "message", "无效的 token");
        }

        Optional<User> userOptional = userRepository.findByUsername(tokenData.getUsername());
        if (userOptional.isEmpty()) {
            return Map.of("success", false, "message", "无效的用户名");
        }

        User player = userOptional.get();

        Optional<Combat> combatOptional = combatRepository.findById(request.getCombatId());
        if (combatOptional.isEmpty()) {
            return Map.of("success", false, "message", "战斗不存在");
        }

        return combatService.executeOperate(
                request.getCombatId(),
                player,
                request
        );
    }

    @GetMapping("/{combatId}")
    public Map<String, Object> getCombatStatus(@PathVariable Long combatId) {
        Optional<Combat> combatOptional = combatRepository.findById(combatId);
        if (combatOptional.isEmpty()) {
            return Map.of("success", false, "message", "战斗不存在");
        }

        Combat combat = combatOptional.get();

        return Map.of(
                "success", true,
                "data", Map.of(
                        "id", combat.getId(),
                        "name", combat.getName(),
                        "phase", combat.getCombatPhase().name(),
                        "roundNumber", combat.getRoundNumber(),
                        "currentPlayer", combat.getCurrentPlayer() != null ?
                                combat.getCurrentPlayer().getNickname() : "null",
                        "playersCount", combat.getPlayers().size(),
                        "squadsCount", combat.getSquads().size(),
                        "actedSquadsCount", combat.getActiveSquads().size(),
                        "isFinished", combat.isFinished(),
                        "activeSquads", combat.getActiveSquads().values().stream().map(activeSquad ->
                                Map.of(
                                        "id", activeSquad.getId(),
                                        "squadId", activeSquad.getId(),
                                        "squadName", activeSquad.getName(),
                                        "squadOwner", activeSquad.getOwner().getNickname(),
                                        "positionX", activeSquad.getPosX(),
                                        "positionY", activeSquad.getPosY(),
                                        "isActed", activeSquad.isActed(),
                                        "effects", activeSquad.getEffects().stream().map(buff ->
                                                Map.of(
                                                        "id", buff.getId(),
                                                        "name", buff.getFeatures().getName(),
                                                        "description", buff.getFeatures().getDescription(),
                                                        "duration", buff.getDuration()
                                                )
                                        ).toList()
                                )
                        )
                )
        );
    }

    @PostMapping("/{combatId}/unload")
    public Map<String, Object> unloadCombat(@PathVariable Long combatId) {
        combatService.unloadCombat(combatId);
        return Map.of("success", true, "message", "战斗已卸载");
    }
}
