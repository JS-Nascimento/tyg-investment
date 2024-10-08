package br.dev.jstec.tyginvestiment.events;

import br.dev.jstec.tyginvestiment.models.AssetTransaction;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class AssetTransactionSavedEvent extends ApplicationEvent {

    private final AssetTransaction transaction;

    public AssetTransactionSavedEvent(Object source, AssetTransaction transaction) {
        super(source);
        this.transaction = transaction;
    }
}
