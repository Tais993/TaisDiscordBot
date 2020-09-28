package database.reactionroles;

public class RoleEmojiObject {
    String roleId;
    String emojiId;

    public RoleEmojiObject(String roleId, String emojiId) {
        this.roleId = roleId;
        this.emojiId = emojiId;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getEmojiId() {
        return emojiId;
    }
}
