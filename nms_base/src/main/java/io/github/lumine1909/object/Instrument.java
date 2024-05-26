package io.github.lumine1909.object;

import java.util.function.Consumer;

public class Instrument {
    private org.bukkit.Instrument instrument;
    private String key;
    private boolean error = false;
    public Instrument(org.bukkit.Instrument bkIns) {
        if (bkIns == null) {
            key = "null";
            this.instrument = null;
        } else {
            this.instrument = bkIns;
            this.key = bkIns.name();
        }
    }
    public Instrument(String instrumentName) {
        if (instrumentName.equals("null")) {
            key = "null";
            this.instrument = null;
        } else {
            try {
                this.instrument = org.bukkit.Instrument.valueOf( instrumentName.toUpperCase());
                this.key = instrumentName;
            } catch (IllegalArgumentException e) {
                this.error = true;
                this.key = null;
                this.instrument = null;
            }
        }
    }
    public boolean isNull() {
        return key.equals("null");
    }

    public org.bukkit.Instrument getInstrument() {
        return instrument;
    }
    public boolean checkError(Runnable r) {
        if (error) {
            r.run();
            return true;
        }
        return false;
    }
    public boolean checkError() {
        return checkError(() -> {});
    }
}
