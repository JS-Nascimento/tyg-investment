package br.dev.jstec.tyginvestiment.dto.accountsummary;

import br.dev.jstec.tyginvestiment.enums.AssetType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@SqlResultSetMapping(
        name = "PortfolioOverviewDto",
        classes = @ConstructorResult(
                targetClass = PortfolioOverviewDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "type", type = AssetType.class),
                        @ColumnResult(name = "asset", type = String.class),
                        @ColumnResult(name = "currency", type = String.class),
                        @ColumnResult(name = "initialQuantity", type = BigDecimal.class),
                        @ColumnResult(name = "quantity", type = BigDecimal.class),
                        @ColumnResult(name = "initialPrice", type = BigDecimal.class),
                        @ColumnResult(name = "purchaseDate", type = LocalDate.class),
                        @ColumnResult(name = "price", type = BigDecimal.class),
                        @ColumnResult(name = "priceDate", type = LocalDate.class),
                        @ColumnResult(name = "value", type = BigDecimal.class),
                        @ColumnResult(name = "profit", type = BigDecimal.class),
                        @ColumnResult(name = "rate", type = BigDecimal.class),
                        @ColumnResult(name = "rateDate", type = LocalDateTime.class)

                }
        )
)
@NamedNativeQuery(
        name = "AccountHoldingRepository.findAccountHoldingWithDetails",
        query = """
                WITH LatestStockQuotation AS (
                    SELECT st.stock_id,
                           st.close AS latest_price,
                           st.date AS latest_date
                    FROM stockquotation st
                    WHERE (st.stock_id, st.date) IN (
                        SELECT stock_id, MAX(date)
                        FROM stockquotation
                        GROUP BY stock_id
                    )
                ),
                LatestConversionRate AS (
                    SELECT c.code AS currency,
                           cv.rate AS latest_rate,
                           cv.ratedate AS latest_date
                    FROM conversion_rates cv
                    JOIN currencies c ON c.id = cv.target_currency_id
                    WHERE (c.code, cv.ratedate) IN (
                        SELECT c.code, MAX(cv.ratedate)
                        FROM conversion_rates cv
                        JOIN currencies c ON c.id = cv.target_currency_id
                        GROUP BY c.code
                    )
                )
                SELECT ah.id,
                       ast.symbol AS asset,
                       ast.assettype AS type,
                       ast.currency AS currency,
                       ah.initialquantity AS initialQuantity,
                       ah.quantity AS quantity,
                       ah.initialprice AS initialPrice,
                       ah.purchasedate AS purchaseDate,
                       lst.latest_price AS price,
                       lst.latest_date AS priceDate,
                       ah.quantity * lst.latest_price AS value,
                       ah.quantity * lst.latest_price - ah.initialprice * ah.quantity AS profit,
                       lcr.latest_rate AS rate,
                       lcr.latest_date AS rateDate
                FROM account_holdings ah
                LEFT JOIN assets ast ON ah.asset_id = ast.symbol
                LEFT JOIN LatestStockQuotation lst ON ah.asset_id = lst.stock_id
                LEFT JOIN LatestConversionRate lcr ON ast.currency = lcr.currency
                WHERE ah.account_id = :accountId
                """,
        resultSetMapping = "PortfolioOverviewDto"
)
public class PortfolioOverviewDto {

    @Id
    private Long id;

    private AssetType type;

    private String asset;

    private String currency;

    private BigDecimal initialQuantity;

    private BigDecimal quantity;

    private BigDecimal initialPrice;

    private LocalDate purchaseDate;

    private BigDecimal price;

    private LocalDate priceDate;

    private BigDecimal value;

    private BigDecimal profit;

    private BigDecimal rate;

    private LocalDateTime rateDate;

    private BigDecimal valueConverted;

    public BigDecimal getValueConverted() {
        return value.divide(rate, MathContext.DECIMAL128).setScale(3, RoundingMode.HALF_UP);
    }
}
