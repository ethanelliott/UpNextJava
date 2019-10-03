package ca.ethanelliott.upnext.server.upnext;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class PartyBuilder {
    private static String generateCode() {
        String chars = "abcdefghijklmnpqrstuvwxyz1234567890".toUpperCase();
        Random random = new Random(new Date().getTime());
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }

    public static Party build(String name, String adminPassword, String token, String refreshToken, double tokenExpiry, String userID) {
        Party party = new Party();
        party.setUuid(UUID.randomUUID().toString());
        party.setName(name);
        party.setCode(generateCode());
        party.setStart(new Date().getTime());
        party.setBackgroundColour("#303030");
        party.setProgressColour("#ff0000");
        party.setAdminPassword(adminPassword);
        party.setToken(token);
        party.setRefreshToken(refreshToken);
        party.setTokenExpiry(tokenExpiry);
        party.setUserID(userID);
        party.setPlaylist(new ArrayList<>());
        party.setCurrentTrack(null);
        party.setPlayState(false);
        party.setUsers(new ArrayList<>());
        party.setVoteSkipList(new ArrayList<>());
        party.setHistory(new ArrayList<>());
        return party;
    }
}
