package com.lightending.mixinHelper;

public class EntityHelper {
    public static boolean globalIsBinded;
    public static void transIsBinded(boolean isBinded) {
        globalIsBinded = isBinded;
    }
}
