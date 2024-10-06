package br.dev.jstec.tyginvestiment.events;

import br.dev.jstec.tyginvestiment.models.Asset;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class AssetSavedEvent extends ApplicationEvent {

    private final Asset asset;

    public AssetSavedEvent(Object source, Asset asset) {
        super(source);
        this.asset = asset;
    }
}
