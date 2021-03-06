package com.neelhridoy.pdf.scanner.payslip;

import technology.tabula.*;
import technology.tabula.extractors.ExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Author: Pradatta Adhikary
 * Date: 9/24/2015
 * Time: 5:02 PM
 */

class PaySlipV2 extends PaySlip {
    PaySlipV2(File file, String password) throws IOException {
        super(file, password);
    }


    @Override
    void analyse() {
        PageIterator it = objectExtractor.extract();
        while (it.hasNext()) {
            Page page = it.next();

            for (Table table : extractionAlgorithm.extract(page)) {

                String startingValue = table.getCell(1, 0).getText(false);

                if (startingValue != null && startingValue.startsWith(DATE_START_LINE)) {
                    processDate(table);

                    processDesignation(table);

                    basicEarning = parseInt(table.getCell(7, 0).getText(false));
                    hra = parseInt(table.getCell(7, 1).getText(false));
                    conveyanceAllowance = parseInt(table.getCell(7, 2).getText(false));
                    medicalAllowance = parseInt(table.getCell(7, 3).getText(false));
                    lta = parseInt(table.getCell(7, 4).getText(false));
                    cca = parseInt(table.getCell(7, 5).getText(false));
                    totalEarnings = parseInt(table.getCell(7, 6).getText(false));
                } else if (startingValue != null && startingValue.startsWith(PROVIDENT_FUND)) {

                    pFund = parseInt(table.getCell(2, 0).getText(false));
                    pTax = parseInt(table.getCell(2, 1).getText(false));
                    iTax = parseInt(table.getCell(2, 2).getText(false));
                    other = parseInt(table.getCell(2, 3).getText(false));
                    totalDeduction = parseInt(table.getCell(2, 4).getText(false));

                } else {
                    netPayable = parseInt(table.getCell(1, 1).getText(false));
                    break;
                }
            }
        }
    }
}
