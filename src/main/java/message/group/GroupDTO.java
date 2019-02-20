package message.group;

/**
 * 群组数据类
 */
public class GroupDTO {
    /**
     * 创建用户ID
     */
    private String createUserId;

    /**
     * 创建群组ID
     */
    private String  groupId;

    /**
     * 群组 Id 对应的名称
     */
    private String groupName;

    /**
     * 群成员
     */
    private String[] userIds;


    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }
}
