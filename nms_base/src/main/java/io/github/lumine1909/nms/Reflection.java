package io.github.lumine1909.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;


public abstract class Reflection {

    boolean debug = false;
    // 1.12.2 -> 1_12_R1
    String sv = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    // 1, XX, Rxx
    String[] strvers = sv.split("_");
    // 1_12_R1 -> 1201
    int ver = Integer.parseInt(strvers[1]) * 100 + Integer.parseInt(strvers[2].substring(1));
    Class<?> obcIsC;
    Class<?> obcBlockC;
    Class<?> obcPlayerC;
    Class<?> obcWorldC;
    Class<?> nmsIsC;
    Class<?> nbtTagC;
    Class<?> pConnC;
    Class<?> remReasonC;
    Class<?> nmsWorldC;
    Class<?> nmsEntityC;
    Class<?> nmsDataC;
    Class<?> nmsPlayerC;
    Class<?> packAddEC;
    Class<?> packEDataC;
    Class<?> packRemEC;
    //Method isHandleM;
    Method playerHandleM;
    Method setIntM;
    Method asCbMM;
    Method asNmsCM;
    Method getIntM;
    Method packAllM;
    Method getDataM;
    Method sPackM;
    Method nmsTagM;
    Method worldHandleM;
    Field pConnF;
    Field isAliveF;
    Field isValidF;
    Constructor<?> packEDataCt;


    int id = 1145141234;
    UUID uuid = UUID.randomUUID();
    Object nmsArmor;
    ArmorStand bukkitArmor;
    public Reflection() {
        obcIsC = obcClass("inventory.CraftItemStack");
        obcBlockC = obcClass("block.CraftBlock");
        obcPlayerC = obcClass("entity.CraftPlayer");
        obcWorldC = obcClass("CraftWorld");
        playerHandleM = method(obcPlayerC, "getHandle");
    }
    protected Method method(Class<?> clazz, String name, Class<?>... paras) {
        try {
            Method m = clazz.getDeclaredMethod(name, paras);
            m.setAccessible(true);
            return m;
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
            return null;
        }
    }
    protected Constructor<?> cons(Class<?> clazz, Class<?>... paras) {
        try {
            return clazz.getDeclaredConstructor(paras);
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
            return null;
        }
    }
    protected Object newIns(Constructor<?> cons, Object... args) {
        try {
            return cons.newInstance(args);
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
            return null;
        }
    }
    protected Field field(Class<?> clazz, String name) {
        try {
            Field f = clazz.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        } catch (Exception e) {
            return null;
        }
    }
    protected Object invoke(Method m, Object obj, Object... args) {
        try {
            return m.invoke(obj, args);
        } catch (Exception e) {
            return null;
        }
    }
    protected Object get(Field f, Object obj) {
        try {
            return f.get(obj);
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
            return null;
        }
    }
    protected void set(Field f, Object obj, Object value) {
        try {
            f.set(obj, value);
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
        }
    }
    protected Class<?> clazz(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
            return null;
        }
    }
    protected Class<?> obcClass(String name) {
        return clazz("org.bukkit.craftbukkit." + sv + "." + name);
    }
    abstract protected Class<?> nmsClass(String name);
}
