package br.dev.jstec.tyginvestiment.repository;

import br.dev.jstec.tyginvestiment.dto.accountsummary.PortfolioOverviewDto;
import br.dev.jstec.tyginvestiment.models.AccountHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AccountHoldingRepository extends JpaRepository<AccountHolding, Long> {

    @Query ("""
           SELECT ah.id,
                                                                           ah.account.id,
                                                                           ah.asset.id,
                                                                           ast.currency,
                                                                           ah.initialQuantity,
                                                                           ah.quantity,
                                                                           ah.initialPrice,
                                                                           ah.purchaseDate,
                                                                           (
                                                                               SELECT st.close
                                                                               FROM StockQuotation st
                                                                               WHERE st.stock.id = ah.asset.id
                                                                               AND st.date = (
                                                                                   SELECT MAX(st2.date)
                                                                                   FROM StockQuotation st2
                                                                                   WHERE st2.stock.id = ah.asset.id
                                                                               )
                                                                           ) AS price ,
                                                                           (ah.quantity * (
                                                                               SELECT st.close
                                                                               FROM StockQuotation st
                                                                               WHERE st.stock.id = ah.asset.id
                                                                               AND st.date = (
                                                                                   SELECT MAX(st2.date)
                                                                                   FROM StockQuotation st2
                                                                                   WHERE st2.stock.id = ah.asset.id
                                                                               )
                                                                           )) AS value ,
                                                                           (ah.quantity * (
                                                                               SELECT st.close
                                                                               FROM StockQuotation st
                                                                               WHERE st.stock.id = ah.asset.id
                                                                               AND st.date = (
                                                                                   SELECT MAX(st2.date)
                                                                                   FROM StockQuotation st2
                                                                                   WHERE st2.stock.id = ah.asset.id
                                                                               )
                                                                           ) - (ah.initialPrice * ah.quantity)) AS profit,
                                                                           (
                                                                               SELECT cv.rate
                                                                               FROM ConversionRate cv
                                                                               JOIN cv.targetCurrency c
                                                                               WHERE c.code = ast.currency
                                                                               AND cv.rateDate = (
                                                                                   SELECT MAX(cv2.rateDate)
                                                                                   FROM ConversionRate cv2
                                                                                   WHERE cv2.targetCurrency.code = ast.currency
                                                                               )
                                                                           ) AS rate
                                                                    FROM AccountHoldings ah
                                                                    LEFT JOIN ah.asset ast
                                                                    WHERE ah.id = :id
                                                                    
        """)
    PortfolioOverviewDto getAccountHoldingById(Long id);
}
