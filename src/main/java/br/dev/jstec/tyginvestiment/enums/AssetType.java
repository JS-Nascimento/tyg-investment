package br.dev.jstec.tyginvestiment.enums;

public enum AssetType {

    STOCK,
    ADR,
    CRYPTO,
    CURRENCY,
    COMMODITY,
    BOND,
    FUND,
    INDEX,
    ETF,
    OPTION,
    FUTURE,
    OTHER;

    public static AssetType fromString(String type) {
        for (AssetType assetType : AssetType.values()) {
            if (assetType.name().equalsIgnoreCase(type)) {
                return assetType;
            }
        }
        return OTHER;
    }

    public static boolean isCurrency(AssetType assetType) {
        return assetType == CURRENCY;
    }

    public static boolean isCrypto(AssetType assetType) {
        return assetType == CRYPTO;
    }

    public static boolean isStock(AssetType assetType) {
        return assetType == STOCK;
    }
    public static AssetType isStock(String assetType) {
        if (assetType == null) {
            return OTHER;
        }
        return assetType.toUpperCase().contains(STOCK.name()) ? STOCK : OTHER;
    }

    public static boolean isCommodity(AssetType assetType) {
        return assetType == COMMODITY;
    }

    public static boolean isBond(AssetType assetType) {
        return assetType == BOND;
    }

    public static boolean isFund(AssetType assetType) {
        return assetType == FUND;
    }

    public static boolean isIndex(AssetType assetType) {
        return assetType == INDEX;
    }

    public static boolean isEtf(AssetType assetType) {
        return assetType == ETF;
    }

    public static boolean isOption(AssetType assetType) {
        return assetType == OPTION;
    }

    public static boolean isFuture(AssetType assetType) {
        return assetType == FUTURE;
    }

    public static boolean isAdr(AssetType assetType) {
        return assetType == ADR;
    }

    public static boolean isOther(AssetType assetType) {
        return assetType == OTHER;
    }

    public static String getBaseType(String assetType) {
        if (assetType == null) {
            return OTHER.name().toUpperCase();
        }

        if (assetType.toUpperCase().contains(STOCK.name())) {
            return STOCK.name().toUpperCase();
        }
        if (assetType.toUpperCase().contains(ADR.name())) {
            return STOCK.name().toUpperCase();
        }
        if (assetType.toUpperCase().contains(CRYPTO.name())) {
            return CRYPTO.name().toUpperCase();
        }
        if (assetType.toUpperCase().contains(CURRENCY.name())) {
            return CURRENCY.name().toUpperCase();
        }
        if (assetType.toUpperCase().contains(COMMODITY.name())) {
            return COMMODITY.name().toUpperCase();
        }
        if (assetType.toUpperCase().contains(BOND.name())) {
            return BOND.name().toUpperCase();
        }
        if (assetType.toUpperCase().contains(FUND.name())) {
            return FUND.name().toUpperCase();
        }
        if (assetType.toUpperCase().contains(INDEX.name())) {
            return INDEX.name().toUpperCase();
        }
        if (assetType.toUpperCase().contains(ETF.name())) {
            return ETF.name().toUpperCase();
        }
        if (assetType.toUpperCase().contains(OPTION.name())) {
            return OPTION.name().toUpperCase();
        }
        if (assetType.toUpperCase().contains(FUTURE.name())) {
            return FUTURE.name().toUpperCase();
        }
        return OTHER.name().toUpperCase();
    }

}
