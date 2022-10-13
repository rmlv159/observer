package com.nordea.emarkets.fx.monitoring;

import com.nordea.emarkets.fx.monitoring.adaptorlayer.Observer;
import com.nordea.emarkets.fx.monitoring.adaptorlayer.Sink;
import com.nordea.emarkets.fx.monitoring.adaptorlayer.Source;

public class Application {
    private Source source;
    private Sink sink;
    private Observer observer;

    public Application(Source source) {
        this.source = source;
        sink = new Output();
        observer = createObserver(sink);
    }

    public void start() {
        source.subscribe(observer);
    }

    private Observer createObserver(Sink sink) {
        return new EventProcessor(sink);
    }

}
