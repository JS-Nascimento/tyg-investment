package br.dev.jstec.tyginvestiment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AssetMarketLocation {
    BR,
    US,
    EU;

    public static String getCurrency(AssetMarketLocation location) {
        return switch (location) {
            case US -> "USD";
            case EU -> "EUR";
            case BR -> "BRL";
        };
    }
}

