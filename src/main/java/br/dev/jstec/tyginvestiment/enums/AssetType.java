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

}
