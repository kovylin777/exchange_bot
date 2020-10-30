package requester;

import lombok.Getter;
import models.Company;
import requester.managers.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class CompanyProvider {

    private static ArrayList<Company> companies;

    @Getter
    private static Date actualityDate;

    @Getter
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");

    public static boolean isUpdatingNow = false;

    public void setCompanies() {
        isUpdatingNow = true;
        actualityDate = new Date(System.currentTimeMillis());
        companies = new ArrayList<>();
        companies.add(new ObmenkaKhUaManager().getCompany());
        companies.add(new KharkovObmenkaUaManager().getCompany());
        companies.add(new Money24Manager().getCompany());
        companies.add(new ObmenkaKharkovUaManager().getCompany());
        companies.add(new ObmenkaKharkivUaManager().getCompany());
        isUpdatingNow = false;
    }

    public Company getBestBuyingCompanyFor(String currencyCode) {
        Map<Integer, BigDecimal> currencyRates = new HashMap<>();
        for (Company company : companies) {
            try {
                currencyRates.put(company.getId(), company.getCurrencyRates()
                    .stream().filter(e -> e.getCode().equals(currencyCode))
                    .findFirst().get().getBuyingRate());
            } catch (NoSuchElementException e) {
                System.out.println("Company with ID " + company.getId() + " not contains data for " + currencyCode);
            }
        }
        Integer topCompanyId = Collections.max(currencyRates.entrySet(), Map.Entry.comparingByValue()).getKey();
        return companies.stream().filter(e -> e.getId() == topCompanyId).findFirst().get();
    }

    public Company getBestSellingCompanyFor(String currencyCode) {
        Map<Integer, BigDecimal> currencyRates = new HashMap<>();
        for (Company company : companies) {
            try {
                currencyRates.put(company.getId(), company.getCurrencyRates()
                    .stream().filter(e -> e.getCode().equals(currencyCode))
                    .findFirst().get().getSellingRate());
            } catch (NoSuchElementException e) {
                System.out.println("Company with ID " + company.getId() + " not contains data for " + currencyCode);
            }
        }
        Integer topCompanyId = Collections.min(currencyRates.entrySet(), Map.Entry.comparingByValue()).getKey();
        return companies.stream().filter(e -> e.getId() == topCompanyId).findFirst().get();
    }
}
