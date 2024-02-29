package aop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Author: Johnny
 * Date: 2017/10/7
 * Time: 20:15
 */
@Getter
@AllArgsConstructor
public enum ActionType {
    //
    INSERT("添加", 1),

    UPDATE("更新", 2),

    DELETE("删除", 3);

    private final String name;

    private final int index;

    public static String getName(int index) {
        for (ActionType actionType : ActionType.values()) {
            if (actionType.getIndex() == index) {
                return actionType.name;
            }
        }
        return null;
    }

    public static Integer getIndex(String name) {
        for (ActionType actionType : ActionType.values()) {
            if (actionType.getName().equals(name)) {
                return actionType.index;
            }
        }
        return null;
    }

    public static ActionType getActionType(int index) {
        for (ActionType actionType : ActionType.values()) {
            if (actionType.getIndex() == index) {
                return actionType;
            }
        }
        return null;
    }

}
