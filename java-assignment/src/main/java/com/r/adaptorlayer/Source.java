package com.nordea.emarkets.fx.monitoring.adaptorlayer;

public interface Source {
    void subscribe(Observer observer);
}
