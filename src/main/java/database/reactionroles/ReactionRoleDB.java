package database.reactionroles;

public class ReactionRoleDB {
    String messageId;
    String textChannelId;
    RoleEmojiObject[] roleEmojiObject;

    public ReactionRoleDB(String messageId, String textChannelId, RoleEmojiObject[] roleEmojiObject) {
        this.messageId = messageId;
        this.textChannelId = textChannelId;
        this.roleEmojiObject = roleEmojiObject;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getTextChannelId() {
        return textChannelId;
    }

    public RoleEmojiObject[] getRoleEmojiObject() {
        return roleEmojiObject;
    }
}
