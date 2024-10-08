package com.nojom.client.ccp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.nojom.client.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


/**
 * Created by hbb20 on 11/1/16.
 */
public class CCPCountry implements Comparable<CCPCountry> {
    static int DEFAULT_FLAG_RES = -99;
    static String TAG = "Class Country";
    static CountryCodePicker.Language loadedLibraryMasterListLanguage;
    static String dialogTitle, searchHintMessage, noResultFoundAckMessage;
    static List<CCPCountry> loadedLibraryMaterList;
    //countries with +1
    private static String ANTIGUA_AND_BARBUDA_AREA_CODES = "268";
    private static String ANGUILLA_AREA_CODES = "264";
    private static String BARBADOS_AREA_CODES = "246";
    private static String BERMUDA_AREA_CODES = "441";
    private static String BAHAMAS_AREA_CODES = "242";
    private static String CANADA_AREA_CODES = "204/226/236/249/250/289/306/343/365/403/416/418/431/437/438/450/506/514/519/579/581/587/600/601/604/613/639/647/705/709/769/778/780/782/807/819/825/867/873/902/905/";
    private static String DOMINICA_AREA_CODES = "767";
    private static String DOMINICAN_REPUBLIC_AREA_CODES = "809/829/849";
    private static String GRENADA_AREA_CODES = "473";
    private static String JAMAICA_AREA_CODES = "876";
    private static String SAINT_KITTS_AND_NEVIS_AREA_CODES = "869";
    private static String CAYMAN_ISLANDS_AREA_CODES = "345";
    private static String SAINT_LUCIA_AREA_CODES = "758";
    private static String MONTSERRAT_AREA_CODES = "664";
    private static String PUERTO_RICO_AREA_CODES = "787";
    private static String SINT_MAARTEN_AREA_CODES = "721";
    private static String TURKS_AND_CAICOS_ISLANDS_AREA_CODES = "649";
    private static String TRINIDAD_AND_TOBAGO_AREA_CODES = "868";
    private static String SAINT_VINCENT_AND_THE_GRENADINES_AREA_CODES = "784";
    private static String BRITISH_VIRGIN_ISLANDS_AREA_CODES = "284";
    private static String US_VIRGIN_ISLANDS_AREA_CODES = "340";

    //countries with +44
    private static String ISLE_OF_MAN = "1624";
    String nameCode;
    String phoneCode;
    boolean isSelect;
    String name, englishName;
    int flagResID = DEFAULT_FLAG_RES;
    public boolean isSelected;

    public CCPCountry() {

    }

    public CCPCountry(String nameCode, String phoneCode, String name, String nameEn, int flagResID, boolean isSelect) {
        this.nameCode = nameCode;
        this.phoneCode = phoneCode;
        this.name = name;
        this.englishName = nameEn;
        this.flagResID = flagResID;
        this.isSelect = isSelect;
    }

    static CountryCodePicker.Language getLoadedLibraryMasterListLanguage() {
        return loadedLibraryMasterListLanguage;
    }

    static void setLoadedLibraryMasterListLanguage(CountryCodePicker.Language loadedLibraryMasterListLanguage) {
        CCPCountry.loadedLibraryMasterListLanguage = loadedLibraryMasterListLanguage;
    }

    public static List<CCPCountry> getLoadedLibraryMaterList() {
        return loadedLibraryMaterList;
    }

    static void setLoadedLibraryMaterList(List<CCPCountry> loadedLibraryMaterList) {
        CCPCountry.loadedLibraryMaterList = loadedLibraryMaterList;
    }

    /**
     * This function parses the raw/countries.xml file, and get list of all the countries.
     *
     * @param context: required to access application resources (where country.xml is).
     * @return List of all the countries available in xml file.
     */
    private static void loadDataFromXML(Context context, CountryCodePicker.Language language) {
        List<CCPCountry> countries = new ArrayList<CCPCountry>();
        String tempDialogTitle = "", tempSearchHint = "", tempNoResultAck = "";
        try {
            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlFactoryObject.newPullParser();
            InputStream ins = context.getResources().openRawResource(context.getResources().getIdentifier(language
                    .toString().toLowerCase(Locale.ROOT), "raw", context.getPackageName()));
            xmlPullParser.setInput(ins, "UTF-8");
            int event = xmlPullParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = xmlPullParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals("country")) {
                            CCPCountry ccpCountry = new CCPCountry();
                            ccpCountry.setNameCode(xmlPullParser.getAttributeValue(null, "name_code").toUpperCase());
                            ccpCountry.setPhoneCode(xmlPullParser.getAttributeValue(null, "phone_code"));
                            ccpCountry.setEnglishName(xmlPullParser.getAttributeValue(null, "english_name"));
                            ccpCountry.setName(xmlPullParser.getAttributeValue(null, "name"));
                            countries.add(ccpCountry);
                        } else if (name.equals("ccp_dialog_title")) {
                            tempDialogTitle = xmlPullParser.getAttributeValue(null, "translation");
                        } else if (name.equals("ccp_dialog_search_hint_message")) {
                            tempSearchHint = xmlPullParser.getAttributeValue(null, "translation");
                        } else if (name.equals("ccp_dialog_no_result_ack_message")) {
                            tempNoResultAck = xmlPullParser.getAttributeValue(null, "translation");
                        }
                        break;
                }
                event = xmlPullParser.next();
            }
            loadedLibraryMasterListLanguage = language;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //if anything went wrong, countries will be loaded for english language
        if (countries.size() == 0) {
//            loadedLibraryMasterListLanguage = CountryCodePicker.Language.ENGLISH;
            countries = getLibraryMasterCountriesEnglish(context);
        }

        dialogTitle = tempDialogTitle.length() > 0 ? tempDialogTitle : "Select a country";
        searchHintMessage = tempSearchHint.length() > 0 ? tempSearchHint : "Search...";
        noResultFoundAckMessage = tempNoResultAck.length() > 0 ? tempNoResultAck : "Results not found";
        loadedLibraryMaterList = countries;

        // sort list
        Collections.sort(loadedLibraryMaterList);
    }

    static String getDialogTitle(Context context, CountryCodePicker.Language language) {
        if (loadedLibraryMasterListLanguage == null || loadedLibraryMasterListLanguage != language || dialogTitle == null || dialogTitle.length() == 0) {
            loadDataFromXML(context, language);
        }
        return dialogTitle;
    }

    static String getSearchHintMessage(Context context, CountryCodePicker.Language language) {
        if (loadedLibraryMasterListLanguage == null || loadedLibraryMasterListLanguage != language || searchHintMessage == null || searchHintMessage.length() == 0) {
            loadDataFromXML(context, language);
        }
        return searchHintMessage;
    }

    static String getNoResultFoundAckMessage(Context context, CountryCodePicker.Language language) {
        if (loadedLibraryMasterListLanguage == null || loadedLibraryMasterListLanguage != language || noResultFoundAckMessage == null || noResultFoundAckMessage.length() == 0) {
            loadDataFromXML(context, language);
        }
        return noResultFoundAckMessage;
    }

    public static void setDialogTitle(String dialogTitle) {
        CCPCountry.dialogTitle = dialogTitle;
    }

    public static void setSearchHintMessage(String searchHintMessage) {
        CCPCountry.searchHintMessage = searchHintMessage;
    }

    public static void setNoResultFoundAckMessage(String noResultFoundAckMessage) {
        CCPCountry.noResultFoundAckMessage = noResultFoundAckMessage;
    }

    /**
     * Search a country which matches @param code.
     *
     * @param context
     * @param preferredCountries is list of preference countries.
     * @param code               phone code. i.e "91" or "1"
     * @return Country that has phone code as @param code.
     * or returns null if no country matches given code.
     * if same code (e.g. +1) available for more than one country ( US, canada) , this function will return preferred country.
     */
    private static CCPCountry getCountryForCode(Context context, CountryCodePicker.Language language, List<CCPCountry> preferredCountries, String code) {

        /**
         * check in preferred countries
         */
        if (preferredCountries != null && !preferredCountries.isEmpty()) {
            for (CCPCountry CCPCountry : preferredCountries) {
                if (CCPCountry.getPhoneCode().equals(code)) {
                    return CCPCountry;
                }
            }
        }

        for (CCPCountry CCPCountry : getLibraryMasterCountryList(context, language)) {
            if (CCPCountry.getPhoneCode().equals(code)) {
                return CCPCountry;
            }
        }
        return null;
    }

    /**
     * @param code phone code. i.e "91" or "1"
     * @return Country that has phone code as @param code.
     * or returns null if no country matches given code.
     * if same code (e.g. +1) available for more than one country ( US, canada) , this function will return preferred country.
     * @avoid Search a country which matches @param code. This method is just to support correct preview
     */
    static CCPCountry getCountryForCodeFromEnglishList(String code, Context context) {

        List<CCPCountry> countries;
        countries = getLibraryMasterCountriesEnglish(context);

        for (CCPCountry ccpCountry : countries) {
            if (ccpCountry.getPhoneCode().equals(code)) {
                return ccpCountry;
            }
        }
        return null;
    }

    static List<CCPCountry> getCustomMasterCountryList(Context context, CountryCodePicker codePicker) {
        codePicker.refreshCustomMasterList();
        if (codePicker.customMasterCountriesList != null && codePicker.customMasterCountriesList.size() > 0) {
            return codePicker.getCustomMasterCountriesList();
        } else {
            return getLibraryMasterCountryList(context, codePicker.getLanguageToApply());
        }
    }

    /**
     * Search a country which matches @param nameCode.
     *
     * @param context
     * @param customMasterCountriesList
     * @param nameCode                  country name code. i.e US or us or Au. See countries.xml for all code names.  @return Country that has phone code as @param code.
     */
    static CCPCountry getCountryForNameCodeFromCustomMasterList(Context context, List<CCPCountry> customMasterCountriesList, CountryCodePicker.Language language, String nameCode) {
        if (customMasterCountriesList == null || customMasterCountriesList.size() == 0) {
            return getCountryForNameCodeFromLibraryMasterList(context, language, nameCode);
        } else {
            for (CCPCountry ccpCountry : customMasterCountriesList) {
                if (ccpCountry.getNameCode().equalsIgnoreCase(nameCode)) {
                    return ccpCountry;
                }
            }
        }
        return null;
    }

    /**
     * Search a country which matches @param nameCode.
     *
     * @param context
     * @param nameCode country name code. i.e US or us or Au. See countries.xml for all code names.
     * @return Country that has phone code as @param code.
     * or returns null if no country matches given code.
     */
    static CCPCountry getCountryForNameCodeFromLibraryMasterList(Context context, CountryCodePicker.Language language, String nameCode) {
        List<CCPCountry> countries;
        countries = CCPCountry.getLibraryMasterCountryList(context, language);
        for (CCPCountry ccpCountry : countries) {
            if (ccpCountry.getNameCode().equalsIgnoreCase(nameCode) || ccpCountry.getName().equalsIgnoreCase(nameCode)) {
                return ccpCountry;
            }
        }
        return null;
    }

    /**
     * Search a country which matches @param nameCode.
     * This searches through local english name list. This should be used only for the preview purpose.
     *
     * @param nameCode country name code. i.e US or us or Au. See countries.xml for all code names.
     * @return Country that has phone code as @param code.
     * or returns null if no country matches given code.
     */
    static CCPCountry getCountryForNameCodeFromEnglishList(String nameCode, Context context) {
        List<CCPCountry> countries;
        countries = getLibraryMasterCountriesEnglish(context);
        for (CCPCountry CCPCountry : countries) {
            if (CCPCountry.getNameCode().equalsIgnoreCase(nameCode)) {
                return CCPCountry;
            }
        }
        return null;
    }

    /**
     * Search a country which matches @param code.
     *
     * @param context
     * @param preferredCountries list of country with priority,
     * @param code               phone code. i.e 91 or 1
     * @return Country that has phone code as @param code.
     * or returns null if no country matches given code.
     */
    static CCPCountry getCountryForCode(Context context, CountryCodePicker.Language language, List<CCPCountry> preferredCountries, int code) {
        return getCountryForCode(context, language, preferredCountries, code + "");
    }

    /**
     * Finds country code by matching substring from left to right from full number.
     * For example. if full number is +819017901357
     * function will ignore "+" and try to find match for first character "8"
     * if any country found for code "8", will return that country. If not, then it will
     * try to find country for "81". and so on till first 3 characters ( maximum number of characters in country code is 3).
     *
     * @param context
     * @param preferredCountries countries of preference
     * @param fullNumber         full number ( "+" (optional)+ country code + carrier number) i.e. +819017901357 / 819017901357 / 918866667722
     * @return Country JP +81(Japan) for +819017901357 or 819017901357
     * Country IN +91(India) for  918866667722
     * null for 2956635321 ( as neither of "2", "29" and "295" matches any country code)
     */
    static CCPCountry getCountryForNumber(Context context, CountryCodePicker.Language language, List<CCPCountry> preferredCountries, String fullNumber) {
        int firstDigit;
        //String plainNumber = PhoneNumberUtil.getInstance().normalizeDigitsOnly(fullNumber);
        if (fullNumber.length() != 0) {
            if (fullNumber.charAt(0) == '+') {
                firstDigit = 1;
            } else {
                firstDigit = 0;
            }
            CCPCountry ccpCountry = null;
            for (int i = firstDigit; i <= fullNumber.length(); i++) {
                String code = fullNumber.substring(firstDigit, i);
                CCPCountryGroup countryGroup = null;
                try {
                    countryGroup = CCPCountryGroup.getCountryGroupForPhoneCode(Integer.parseInt(code));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (countryGroup != null) {
                    int areaCodeStartsAt = firstDigit + code.length();
                    //when phone number covers area code too.
                    if (fullNumber.length() >= areaCodeStartsAt + countryGroup.areaCodeLength) {
                        String areaCode = fullNumber.substring(areaCodeStartsAt, areaCodeStartsAt + countryGroup.areaCodeLength);
                        return countryGroup.getCountryForAreaCode(context, language, areaCode);
                    } else {
                        return getCountryForNameCodeFromLibraryMasterList(context, language, countryGroup.defaultNameCode);
                    }
                } else {
                    ccpCountry = CCPCountry.getCountryForCode(context, language, preferredCountries, code);
                    if (ccpCountry != null) {
                        return ccpCountry;
                    }
                }
            }
        }
        //it reaches here means, phone number has some problem.
        return null;
    }

    /**
     * Finds country code by matching substring from left to right from full number.
     * For example. if full number is +819017901357
     * function will ignore "+" and try to find match for first character "8"
     * if any country found for code "8", will return that country. If not, then it will
     * try to find country for "81". and so on till first 3 characters ( maximum number of characters in country code is 3).
     *
     * @param context
     * @param fullNumber full number ( "+" (optional)+ country code + carrier number) i.e. +819017901357 / 819017901357 / 918866667722
     * @return Country JP +81(Japan) for +819017901357 or 819017901357
     * Country IN +91(India) for  918866667722
     * null for 2956635321 ( as neither of "2", "29" and "295" matches any country code)
     */
    public static CCPCountry getCountryForNumber(Context context, CountryCodePicker.Language language, String fullNumber) {
        return getCountryForNumber(context, language, null, fullNumber);
    }

    /**
     * Returns image res based on country name code
     *
     * @param CCPCountry
     * @return
     */
    private static int getFlagMasterResID(CCPCountry CCPCountry) {
        switch (CCPCountry.getNameCode().toLowerCase()) {
            //this should be sorted based on country name code.
            case "ad": //andorra
                return R.drawable.flag_andorra;
            case "ae": //united arab emirates
                return R.drawable.flag_uae;
            case "af": //afghanistan
                return R.drawable.flag_afghanistan;
            case "ag": //antigua & barbuda
                return R.drawable.flag_antigua_and_barbuda;
            case "ai": //anguilla // Caribbean Islands
                return R.drawable.flag_anguilla;
            case "al": //albania
                return R.drawable.flag_albania;
            case "am": //armenia
                return R.drawable.flag_armenia;
            case "ao": //angola
                return R.drawable.flag_angola;
            case "aq": //antarctica // custom
                return R.drawable.flag_antarctica;
            case "ar": //argentina
                return R.drawable.flag_argentina;
            case "as": //American Samoa
                return R.drawable.flag_american_samoa;
            case "at": //austria
                return R.drawable.flag_austria;
            case "au": //australia
                return R.drawable.flag_australia;
            case "aw": //aruba
                return R.drawable.flag_aruba;
            case "ax": //alan islands
                return R.drawable.flag_aland;
            case "az": //azerbaijan
                return R.drawable.flag_azerbaijan;
            case "ba": //bosnia and herzegovina
                return R.drawable.flag_bosnia;
            case "bb": //barbados
                return R.drawable.flag_barbados;
            case "bd": //bangladesh
                return R.drawable.flag_bangladesh;
            case "be": //belgium
                return R.drawable.flag_belgium;
            case "bf": //burkina faso
                return R.drawable.flag_burkina_faso;
            case "bg": //bulgaria
                return R.drawable.flag_bulgaria;
            case "bh": //bahrain
                return R.drawable.flag_bahrain;
            case "bi": //burundi
                return R.drawable.flag_burundi;
            case "bj": //benin
                return R.drawable.flag_benin;
            case "bl": //saint barthélemy
                return R.drawable.flag_saint_barthelemy;// custom
            case "bm": //bermuda
                return R.drawable.flag_bermuda;
            case "bn": //brunei darussalam // custom
                return R.drawable.flag_brunei;
            case "bo": //bolivia, plurinational state of
                return R.drawable.flag_bolivia;
            case "br": //brazil
                return R.drawable.flag_brazil;
            case "bs": //bahamas
                return R.drawable.flag_bahamas;
            case "bt": //bhutan
                return R.drawable.flag_bhutan;
            case "bw": //botswana
                return R.drawable.flag_botswana;
            case "by": //belarus
                return R.drawable.flag_belarus;
            case "bz": //belize
                return R.drawable.flag_belize;
            case "ca": //canada
                return R.drawable.flag_canada;
            case "cc": //cocos (keeling) islands
                return R.drawable.flag_cocos;// custom
            case "cd": //congo, the democratic republic of the
                return R.drawable.flag_democratic_republic_of_the_congo;
            case "cf": //central african republic
                return R.drawable.flag_central_african_republic;
            case "cg": //congo
                return R.drawable.flag_republic_of_the_congo;
            case "ch": //switzerland
                return R.drawable.flag_switzerland;
            case "ci": //côte d\'ivoire
                return R.drawable.flag_cote_divoire;
            case "ck": //cook islands
                return R.drawable.flag_cook_islands;
            case "cl": //chile
                return R.drawable.flag_chile;
            case "cm": //cameroon
                return R.drawable.flag_cameroon;
            case "cn": //china
                return R.drawable.flag_china;
            case "co": //colombia
                return R.drawable.flag_colombia;
            case "cr": //costa rica
                return R.drawable.flag_costa_rica;
            case "cu": //cuba
                return R.drawable.flag_cuba;
            case "cv": //cape verde
                return R.drawable.flag_cape_verde;
            case "cw": //cape verde
                return R.drawable.flag_curacao;
            case "cx": //christmas island
                return R.drawable.flag_christmas_island;
            case "cy": //cyprus
                return R.drawable.flag_cyprus;
            case "cz": //czech republic
                return R.drawable.flag_czech_republic;
            case "de": //germany
                return R.drawable.flag_germany;
            case "dj": //djibouti
                return R.drawable.flag_djibouti;
            case "dk": //denmark
                return R.drawable.flag_denmark;
            case "dm": //dominica
                return R.drawable.flag_dominica;
            case "do": //dominican republic
                return R.drawable.flag_dominican_republic;
            case "dz": //algeria
                return R.drawable.flag_algeria;
            case "ec": //ecuador
                return R.drawable.flag_ecuador;
            case "ee": //estonia
                return R.drawable.flag_estonia;
            case "eg": //egypt
                return R.drawable.flag_egypt;
            case "er": //eritrea
                return R.drawable.flag_eritrea;
            case "es": //spain
                return R.drawable.flag_spain;
            case "et": //ethiopia
                return R.drawable.flag_ethiopia;
            case "fi": //finland
                return R.drawable.flag_finland;
            case "fj": //fiji
                return R.drawable.flag_fiji;
            case "fk": //falkland islands (malvinas)
                return R.drawable.flag_falkland_islands;
            case "fm": //micronesia, federated states of
                return R.drawable.flag_micronesia;
            case "fo": //faroe islands
                return R.drawable.flag_faroe_islands;
            case "fr": //france
                return R.drawable.flag_france;
            case "ga": //gabon
                return R.drawable.flag_gabon;
            case "gb": //united kingdom
                return R.drawable.flag_united_kingdom;
            case "gd": //grenada
                return R.drawable.flag_grenada;
            case "ge": //georgia
                return R.drawable.flag_georgia;
            case "gf": //guyane
                return R.drawable.flag_guyane;
            case "gg": //Guernsey
                return R.drawable.flag_guernsey;
            case "gh": //ghana
                return R.drawable.flag_ghana;
            case "gi": //gibraltar
                return R.drawable.flag_gibraltar;
            case "gl": //greenland
                return R.drawable.flag_greenland;
            case "gm": //gambia
                return R.drawable.flag_gambia;
            case "gn": //guinea
                return R.drawable.flag_guinea;
            case "gp": //guadeloupe
                return R.drawable.flag_guadeloupe;
            case "gq": //equatorial guinea
                return R.drawable.flag_equatorial_guinea;
            case "gr": //greece
                return R.drawable.flag_greece;
            case "gt": //guatemala
                return R.drawable.flag_guatemala;
            case "gu": //Guam
                return R.drawable.flag_guam;
            case "gw": //guinea-bissau
                return R.drawable.flag_guinea_bissau;
            case "gy": //guyana
                return R.drawable.flag_guyana;
            case "hk": //hong kong
                return R.drawable.flag_hong_kong;
            case "hn": //honduras
                return R.drawable.flag_honduras;
            case "hr": //croatia
                return R.drawable.flag_croatia;
            case "ht": //haiti
                return R.drawable.flag_haiti;
            case "hu": //hungary
                return R.drawable.flag_hungary;
            case "id": //indonesia
                return R.drawable.flag_indonesia;
            case "ie": //ireland
                return R.drawable.flag_ireland;
            case "il": //israel
                return R.drawable.flag_israel;
            case "im": //isle of man
                return R.drawable.flag_isleof_man; // custom
            case "is": //Iceland
                return R.drawable.flag_iceland;
            case "in": //india
                return R.drawable.flag_india;
            case "io": //British indian ocean territory
                return R.drawable.flag_british_indian_ocean_territory;
            case "iq": //iraq
                return R.drawable.flag_iraq_new;
            case "ir": //iran, islamic republic of
                return R.drawable.flag_iran;
            case "it": //italy
                return R.drawable.flag_italy;
            case "je": //Jersey
                return R.drawable.flag_jersey;
            case "jm": //jamaica
                return R.drawable.flag_jamaica;
            case "jo": //jordan
                return R.drawable.flag_jordan;
            case "jp": //japan
                return R.drawable.flag_japan;
            case "ke": //kenya
                return R.drawable.flag_kenya;
            case "kg": //kyrgyzstan
                return R.drawable.flag_kyrgyzstan;
            case "kh": //cambodia
                return R.drawable.flag_cambodia;
            case "ki": //kiribati
                return R.drawable.flag_kiribati;
            case "km": //comoros
                return R.drawable.flag_comoros;
            case "kn": //st kitts & nevis
                return R.drawable.flag_saint_kitts_and_nevis;
            case "kp": //north korea
                return R.drawable.flag_north_korea;
            case "kr": //south korea
                return R.drawable.flag_south_korea;
            case "kw": //kuwait
                return R.drawable.flag_kuwait;
            case "ky": //Cayman_Islands
                return R.drawable.flag_cayman_islands;
            case "kz": //kazakhstan
                return R.drawable.flag_kazakhstan;
            case "la": //lao people\'s democratic republic
                return R.drawable.flag_laos;
            case "lb": //lebanon
                return R.drawable.flag_lebanon;
            case "lc": //st lucia
                return R.drawable.flag_saint_lucia;
            case "li": //liechtenstein
                return R.drawable.flag_liechtenstein;
            case "lk": //sri lanka
                return R.drawable.flag_sri_lanka;
            case "lr": //liberia
                return R.drawable.flag_liberia;
            case "ls": //lesotho
                return R.drawable.flag_lesotho;
            case "lt": //lithuania
                return R.drawable.flag_lithuania;
            case "lu": //luxembourg
                return R.drawable.flag_luxembourg;
            case "lv": //latvia
                return R.drawable.flag_latvia;
            case "ly": //libya
                return R.drawable.flag_libya;
            case "ma": //morocco
                return R.drawable.flag_morocco;
            case "mc": //monaco
                return R.drawable.flag_monaco;
            case "md": //moldova, republic of
                return R.drawable.flag_moldova;
            case "me": //montenegro
                return R.drawable.flag_of_montenegro;// custom
            case "mf":
                return R.drawable.flag_saint_martin;
            case "mg": //madagascar
                return R.drawable.flag_madagascar;
            case "mh": //marshall islands
                return R.drawable.flag_marshall_islands;
            case "mk": //macedonia, the former yugoslav republic of
                return R.drawable.flag_macedonia;
            case "ml": //mali
                return R.drawable.flag_mali;
            case "mm": //myanmar
                return R.drawable.flag_myanmar;
            case "mn": //mongolia
                return R.drawable.flag_mongolia;
            case "mo": //macao
                return R.drawable.flag_macao;
            case "mp": // Northern mariana islands
                return R.drawable.flag_northern_mariana_islands;
            case "mq": //martinique
                return R.drawable.flag_martinique;
            case "mr": //mauritania
                return R.drawable.flag_mauritania;
            case "ms": //montserrat
                return R.drawable.flag_montserrat;
            case "mt": //malta
                return R.drawable.flag_malta;
            case "mu": //mauritius
                return R.drawable.flag_mauritius;
            case "mv": //maldives
                return R.drawable.flag_maldives;
            case "mw": //malawi
                return R.drawable.flag_malawi;
            case "mx": //mexico
                return R.drawable.flag_mexico;
            case "my": //malaysia
                return R.drawable.flag_malaysia;
            case "mz": //mozambique
                return R.drawable.flag_mozambique;
            case "na": //namibia
                return R.drawable.flag_namibia;
            case "nc": //new caledonia
                return R.drawable.flag_new_caledonia;// custom
            case "ne": //niger
                return R.drawable.flag_niger;
            case "nf": //Norfolk
                return R.drawable.flag_norfolk_island;
            case "ng": //nigeria
                return R.drawable.flag_nigeria;
            case "ni": //nicaragua
                return R.drawable.flag_nicaragua;
            case "nl": //netherlands
                return R.drawable.flag_netherlands;
            case "no": //norway
                return R.drawable.flag_norway;
            case "np": //nepal
                return R.drawable.flag_nepal;
            case "nr": //nauru
                return R.drawable.flag_nauru;
            case "nu": //niue
                return R.drawable.flag_niue;
            case "nz": //new zealand
                return R.drawable.flag_new_zealand;
            case "om": //oman
                return R.drawable.flag_oman;
            case "pa": //panama
                return R.drawable.flag_panama;
            case "pe": //peru
                return R.drawable.flag_peru;
            case "pf": //french polynesia
                return R.drawable.flag_french_polynesia;
            case "pg": //papua new guinea
                return R.drawable.flag_papua_new_guinea;
            case "ph": //philippines
                return R.drawable.flag_philippines;
            case "pk": //pakistan
                return R.drawable.flag_pakistan;
            case "pl": //poland
                return R.drawable.flag_poland;
            case "pm": //saint pierre and miquelon
                return R.drawable.flag_saint_pierre;
            case "pn": //pitcairn
                return R.drawable.flag_pitcairn_islands;
            case "pr": //puerto rico
                return R.drawable.flag_puerto_rico;
            case "ps": //palestine
                return R.drawable.flag_palestine;
            case "pt": //portugal
                return R.drawable.flag_portugal;
            case "pw": //palau
                return R.drawable.flag_palau;
            case "py": //paraguay
                return R.drawable.flag_paraguay;
            case "qa": //qatar
                return R.drawable.flag_qatar;
            case "re": //la reunion
                return R.drawable.flag_martinique; // no exact flag found
            case "ro": //romania
                return R.drawable.flag_romania;
            case "rs": //serbia
                return R.drawable.flag_serbia; // custom
            case "ru": //russian federation
                return R.drawable.flag_russian_federation;
            case "rw": //rwanda
                return R.drawable.flag_rwanda;
            case "sa": //saudi arabia
                return R.drawable.flag_saudi_arabia;
            case "sb": //solomon islands
                return R.drawable.flag_soloman_islands;
            case "sc": //seychelles
                return R.drawable.flag_seychelles;
            case "sd": //sudan
                return R.drawable.flag_sudan;
            case "se": //sweden
                return R.drawable.flag_sweden;
            case "sg": //singapore
                return R.drawable.flag_singapore;
            case "sh": //saint helena, ascension and tristan da cunha
                return R.drawable.flag_saint_helena; // custom
            case "si": //slovenia
                return R.drawable.flag_slovenia;
            case "sk": //slovakia
                return R.drawable.flag_slovakia;
            case "sl": //sierra leone
                return R.drawable.flag_sierra_leone;
            case "sm": //san marino
                return R.drawable.flag_san_marino;
            case "sn": //senegal
                return R.drawable.flag_senegal;
            case "so": //somalia
                return R.drawable.flag_somalia;
            case "sr": //suriname
                return R.drawable.flag_suriname;
            case "st": //sao tome and principe
                return R.drawable.flag_sao_tome_and_principe;
            case "sv": //el salvador
                return R.drawable.flag_el_salvador;
            case "sx": //sint maarten
                return R.drawable.flag_sint_maarten;
            case "sy": //syrian arab republic
                return R.drawable.flag_syria;
            case "sz": //swaziland
                return R.drawable.flag_swaziland;
            case "tc": //turks & caicos islands
                return R.drawable.flag_turks_and_caicos_islands;
            case "td": //chad
                return R.drawable.flag_chad;
            case "tg": //togo
                return R.drawable.flag_togo;
            case "th": //thailand
                return R.drawable.flag_thailand;
            case "tj": //tajikistan
                return R.drawable.flag_tajikistan;
            case "tk": //tokelau
                return R.drawable.flag_tokelau; // custom
            case "tl": //timor-leste
                return R.drawable.flag_timor_leste;
            case "tm": //turkmenistan
                return R.drawable.flag_turkmenistan;
            case "tn": //tunisia
                return R.drawable.flag_tunisia;
            case "to": //tonga
                return R.drawable.flag_tonga;
            case "tr": //turkey
                return R.drawable.flag_turkey;
            case "tt": //trinidad & tobago
                return R.drawable.flag_trinidad_and_tobago;
            case "tv": //tuvalu
                return R.drawable.flag_tuvalu;
            case "tw": //taiwan, province of china
                return R.drawable.flag_taiwan;
            case "tz": //tanzania, united republic of
                return R.drawable.flag_tanzania;
            case "ua": //ukraine
                return R.drawable.flag_ukraine;
            case "ug": //uganda
                return R.drawable.flag_uganda;
            case "us": //united states
                return R.drawable.flag_united_states_of_america;
            case "uy": //uruguay
                return R.drawable.flag_uruguay;
            case "uz": //uzbekistan
                return R.drawable.flag_uzbekistan;
            case "va": //holy see (vatican city state)
                return R.drawable.flag_vatican_city;
            case "vc": //st vincent & the grenadines
                return R.drawable.flag_saint_vicent_and_the_grenadines;
            case "ve": //venezuela, bolivarian republic of
                return R.drawable.flag_venezuela;
            case "vg": //british virgin islands
                return R.drawable.flag_british_virgin_islands;
            case "vi": //us virgin islands
                return R.drawable.flag_us_virgin_islands;
            case "vn": //vietnam
                return R.drawable.flag_vietnam;
            case "vu": //vanuatu
                return R.drawable.flag_vanuatu;
            case "wf": //wallis and futuna
                return R.drawable.flag_wallis_and_futuna;
            case "ws": //samoa
                return R.drawable.flag_samoa;
            case "xk": //kosovo
                return R.drawable.flag_kosovo;
            case "ye": //yemen
                return R.drawable.flag_yemen;
            case "yt": //mayotte
                return R.drawable.flag_martinique; // no exact flag found
            case "za": //south africa
                return R.drawable.flag_south_africa;
            case "zm": //zambia
                return R.drawable.flag_zambia;
            case "zw": //zimbabwe
                return R.drawable.flag_zimbabwe;
            default:
                return R.drawable.flag_transparent;
        }
    }

    /**
     * This will return all the countries. No preference is manages.
     * Anytime new country need to be added, add it
     *
     * @return
     */
    static List<CCPCountry> getLibraryMasterCountryList(Context context, CountryCodePicker.Language language) {
        if (loadedLibraryMasterListLanguage == null || language != loadedLibraryMasterListLanguage || loadedLibraryMaterList == null || loadedLibraryMaterList.size() == 0) { //when it is required to load country in country list
            loadDataFromXML(context, language);
        }
        return loadedLibraryMaterList;
    }

    public static List<CCPCountry> getLibraryMasterCountriesEnglish(Context context) {
        List<CCPCountry> countries = new ArrayList<>();
        countries.add(new CCPCountry("ad", "376", context.getString(R.string.c_Andorra), "Andorra", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ae", "971", context.getString(R.string.c_United_Arab_Emirates), "United Arab Emirates (UAE)", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("af", "93", context.getString(R.string.c_Afghanistan), "Afghanistan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ag", "1268", context.getString(R.string.c_Antigua_and_Barbuda), "Antigua and Barbuda", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ai", "1264", context.getString(R.string.c_Anguilla), "Anguilla", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("al", "355", context.getString(R.string.c_Albania), "Albania", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("am", "374", context.getString(R.string.c_Armenia), "Armenia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ao", "244", context.getString(R.string.c_Angola), "Angola", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("aq", "672", context.getString(R.string.c_Antarctica), "Antarctica", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ar", "54", context.getString(R.string.c_Argentina), "Argentina", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("as", "1684", context.getString(R.string.c_American_Samoa), "American", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("at", "43", context.getString(R.string.c_Austria), "Austria", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("au", "61", context.getString(R.string.c_Australia), "Australia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("aw", "297", context.getString(R.string.c_Aruba), "Aruba", DEFAULT_FLAG_RES, false));
//        countries.add(new CCPCountry("az", "358", "Aland Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("az", "994", context.getString(R.string.c_Azerbaijan), "Azerbaijan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ba", "387", context.getString(R.string.c_Bosnia_And_Herzegovina), "Bosnia And Herzegovina", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bb", "1246", context.getString(R.string.c_barbados), "Barbados", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bd", "880", context.getString(R.string.c_bangladesh), "Bangladesh", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("be", "32", context.getString(R.string.c_belgium), "Belgium", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bf", "226", context.getString(R.string.c_burkina_faso), "Burkina Faso", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bg", "359", context.getString(R.string.c_bulgaria), "Bulgaria", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bh", "973", context.getString(R.string.c_bahrain), "Bahrain", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bi", "257", context.getString(R.string.c_burundi), "Burundi", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bj", "229", context.getString(R.string.c_benin), "Benin", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bl", "590", context.getString(R.string.c_saint_barthélemy), "Saint Barthélemy", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bm", "1441", context.getString(R.string.c_bermuda), "Bermuda", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bn", "673", context.getString(R.string.c_brunei_darussalam), "Brunei Darussalam", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bo", "591", context.getString(R.string.c_bolivia_plurinational_state_of), "Bolivia, Plurinational State Of", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("br", "55", context.getString(R.string.c_brazil), "Brazil", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bs", "1242", context.getString(R.string.c_bahamas), "Bahamas", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bt", "975", context.getString(R.string.c_bhutan), "Bhutan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bw", "267", context.getString(R.string.c_botswana), "Botswana", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("by", "375", context.getString(R.string.c_belarus), "Belarus", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("bz", "501", context.getString(R.string.c_belize), "Belize", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ca", "1", context.getString(R.string.c_canada), "Canada", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cc", "61", context.getString(R.string.c_cocos_island), "Cocos (keeling) Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cd", "243", context.getString(R.string.c_congo_the), "Congo, The Democratic Republic Of The", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cf", "236", context.getString(R.string.c_central), "Central African Republic", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cg", "242", context.getString(R.string.c_congo), "Congo", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ch", "41", context.getString(R.string.c_switzerland), "Switzerland", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ci", "225", context.getString(R.string.c_cote_d), "Côte D\\'ivoire", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ck", "682", context.getString(R.string.c_cook_island), "Cook Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cl", "56", context.getString(R.string.c_chile), "Chile", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cm", "237", context.getString(R.string.c_cameroon), "Cameroon", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cn", "86", context.getString(R.string.c_china), "China", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("co", "57", context.getString(R.string.c_colombia), "Colombia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cr", "506", context.getString(R.string.c_costa_rica), "Costa Rica", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cu", "53", context.getString(R.string.c_cuba), "Cuba", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cv", "238", context.getString(R.string.c_cape_verde), "Cape Verde", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cw", "599", context.getString(R.string.c_curacao), "Curacao", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cx", "61", context.getString(R.string.c_christmas_island), "Christmas Island", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cy", "357", context.getString(R.string.c_cyprus), "Cyprus", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("cz", "420", context.getString(R.string.c_czech_rebulic), "Czech Republic", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("de", "49", context.getString(R.string.c_germany), "Germany", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("dj", "253", context.getString(R.string.c_djibouti), "Djibouti", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("dk", "45", context.getString(R.string.c_denmark), "Denmark", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("dm", "1767", context.getString(R.string.c_dominica), "Dominica", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("do", "1809", context.getString(R.string.c_dominican_republic), "Dominican Republic", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("do", "1829", context.getString(R.string.c_dominican_republic), "Dominican Republic", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("do", "1849", context.getString(R.string.c_dominican_republic), "Dominican Republic", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("dz", "213", context.getString(R.string.c_algeria), "Algeria", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ec", "593", context.getString(R.string.c_ecuador), "Ecuador", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ee", "372", context.getString(R.string.c_estonia), "Estonia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("eg", "20", context.getString(R.string.c_egypt), "Egypt", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("er", "291", context.getString(R.string.c_eritrea), "Eritrea", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("es", "34", context.getString(R.string.c_spain), "Spain", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("et", "251", context.getString(R.string.c_ethiopia), "Ethiopia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("fi", "358", context.getString(R.string.c_finland), "Finland", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("fj", "679", context.getString(R.string.c_fiji), "Fiji", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("fk", "500", context.getString(R.string.c_falkland_islands), "Falkland Islands (malvinas)", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("fm", "691", context.getString(R.string.c_micronesia), "Micronesia, Federated States Of", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("fo", "298", context.getString(R.string.c_faroe), "Faroe Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("fr", "33", context.getString(R.string.c_france), "France", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ga", "241", context.getString(R.string.c_gabon), "Gabon", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gb", "44", context.getString(R.string.c_united_kingdom), "United Kingdom", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gd", "1473", context.getString(R.string.c_grenada), "Grenada", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ge", "995", context.getString(R.string.c_georgia), "Georgia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gf", "594", context.getString(R.string.c_french_guyana), "French Guyana", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gh", "233", context.getString(R.string.c_ghana), "Ghana", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gi", "350", context.getString(R.string.c_gibraltar), "Gibraltar", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gl", "299", context.getString(R.string.c_greenland), "Greenland", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gm", "220", context.getString(R.string.c_gambia), "Gambia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gn", "224", context.getString(R.string.c_guinea), "Guinea", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gp", "450", context.getString(R.string.c_guadeloupe), "Guadeloupe", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gq", "240", context.getString(R.string.c_Equatorial_Guinea), "Equatorial Guinea", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gr", "30", context.getString(R.string.c_Greece), "Greece", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gt", "502", context.getString(R.string.c_Guatemala), "Guatemala", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gu", "1671", context.getString(R.string.c_Guam), "Guam", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gw", "245", context.getString(R.string.c_Guinea_bissau), "Guinea-bissau", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("gy", "592", context.getString(R.string.c_Guyana), "Guyana", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("hk", "852", context.getString(R.string.c_Hong_Kong), "Hong Kong", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("hn", "504", context.getString(R.string.c_Honduras), "Honduras", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("hr", "385", context.getString(R.string.c_Croatia), "Croatia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ht", "509", context.getString(R.string.c_Haiti), "Haiti", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("hu", "36", context.getString(R.string.c_Hungary), "Hungary", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("id", "62", context.getString(R.string.c_Indonesia), "Indonesia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ie", "353", context.getString(R.string.c_Ireland), "Ireland", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("il", "972", context.getString(R.string.c_Israel), "Israel", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("im", "44", context.getString(R.string.c_Isle_Of_Man), "Isle Of Man", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("is", "354", context.getString(R.string.c_Iceland), "Iceland", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("in", "91", context.getString(R.string.c_India), "India", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("io", "246", context.getString(R.string.c_British_Indian_Ocean_Territory), "British Indian Ocean Territory", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("iq", "964", context.getString(R.string.c_Iraq), "Iraq", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ir", "98", context.getString(R.string.c_Iran_Islamic), "Iran, Islamic Republic Of", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("it", "39", context.getString(R.string.c_Italy), "Italy", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("je", "44", context.getString(R.string.c_Jersey), "Jersey", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("jm", "1876", context.getString(R.string.c_Jamaica), "Jamaica", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("jo", "962", context.getString(R.string.c_Jordan), "Jordan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("jp", "81", context.getString(R.string.c_Japan), "Japan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ke", "254", context.getString(R.string.c_Kenya), "Kenya", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("kg", "996", context.getString(R.string.c_Kyrgyzstan), "Kyrgyzstan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("kh", "855", context.getString(R.string.c_Cambodia), "Cambodia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ki", "686", context.getString(R.string.c_Kiribati), "Kiribati", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("km", "269", context.getString(R.string.c_Comoros), "Comoros", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("kn", "1869", context.getString(R.string.c_Saint_Kitts), "Saint Kitts and Nevis", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("kp", "850", context.getString(R.string.c_North_Korea), "North Korea", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("kr", "82", context.getString(R.string.c_South_Korea), "South Korea", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("kw", "965", context.getString(R.string.c_Kuwait), "Kuwait", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ky", "1345", context.getString(R.string.c_Cayman_Islands), "Cayman Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("kz", "7", context.getString(R.string.c_Kazakhstan), "Kazakhstan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("la", "856", context.getString(R.string.c_Lao_People), "Lao People\\'s Democratic Republic", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("lb", "961", context.getString(R.string.c_Lebanon), "Lebanon", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("lc", "1758", context.getString(R.string.c_Saint_Lucia), "Saint Lucia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("li", "423", context.getString(R.string.c_Liechtenstein), "Liechtenstein", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("lk", "94", context.getString(R.string.c_Sri_Lanka), "Sri Lanka", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("lr", "231", context.getString(R.string.c_Liberia), "Liberia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ls", "266", context.getString(R.string.c_Lesotho), "Lesotho", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("lt", "370", context.getString(R.string.c_Lithuania), "Lithuania", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("lu", "352", context.getString(R.string.c_Luxembourg), "Luxembourg", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("lv", "371", context.getString(R.string.c_Latvia), "Latvia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ly", "218", context.getString(R.string.c_Libya), "Libya", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ma", "212", context.getString(R.string.c_Morocco), "Morocco", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mc", "377", context.getString(R.string.c_Monaco), "Monaco", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("md", "373", context.getString(R.string.c_Moldova), "Moldova, Republic Of", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("me", "382", context.getString(R.string.c_Montenegro), "Montenegro", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mf", "590", context.getString(R.string.c_Saint_Martin), "Saint Martin", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mg", "261", context.getString(R.string.c_Madagascar), "Madagascar", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mh", "692", context.getString(R.string.c_Marshall_Islands), "Marshall Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mk", "389", context.getString(R.string.c_Macedonia), "Macedonia, The Former Yugoslav Republic Of", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ml", "223", context.getString(R.string.c_Mali), "Mali", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mm", "95", context.getString(R.string.c_Myanmar), "Myanmar", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mn", "976", context.getString(R.string.c_Mongolia), "Mongolia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mo", "853", context.getString(R.string.c_Macao), "Macao", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mp", "1670", context.getString(R.string.c_Northern), "Northern Mariana Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mq", "596", context.getString(R.string.c_Martinique), "Martinique", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mr", "222", context.getString(R.string.c_Mauritania), "Mauritania", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ms", "1664", context.getString(R.string.c_Montserrat), "Montserrat", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mt", "356", context.getString(R.string.c_Malta), "Malta", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mu", "230", context.getString(R.string.c_Mauritius), "Mauritius", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mv", "960", context.getString(R.string.c_Maldives), "Maldives", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mw", "265", context.getString(R.string.c_Malawi), "Malawi", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mx", "52", context.getString(R.string.c_Mexico), "Mexico", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("my", "60", context.getString(R.string.c_Malaysia), "Malaysia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("mz", "258", context.getString(R.string.c_Mozambique), "Mozambique", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("na", "264", context.getString(R.string.c_Namibia), "Namibia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("nc", "687", context.getString(R.string.c_New_Caledonia), "New Caledonia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ne", "227", context.getString(R.string.c_Niger), "Niger", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("nf", "672", context.getString(R.string.c_Norfolk_Islands), "Norfolk Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ng", "234", context.getString(R.string.c_Nigeria), "Nigeria", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ni", "505", context.getString(R.string.c_Nicaragua), "Nicaragua", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("nl", "31", context.getString(R.string.c_Netherlands), "Netherlands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("no", "47", context.getString(R.string.c_Norway), "Norway", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("np", "977", context.getString(R.string.c_Nepal), "Nepal", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("nr", "674", context.getString(R.string.c_Nauru), "Nauru", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("nu", "683", context.getString(R.string.c_Niue), "Niue", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("nz", "64", context.getString(R.string.c_New_Zealand), "New Zealand", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("om", "968", context.getString(R.string.c_Oman), "Oman", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pa", "507", context.getString(R.string.c_Panama), "Panama", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pe", "51", context.getString(R.string.c_Peru), "Peru", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pf", "689", context.getString(R.string.c_French_Polynesia), "French Polynesia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pg", "675", context.getString(R.string.c_Papua_New_Guinea), "Papua New Guinea", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ph", "63", context.getString(R.string.c_Philippines), "Philippines", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pk", "92", context.getString(R.string.c_Pakistan), "Pakistan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pl", "48", context.getString(R.string.c_Poland), "Poland", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pm", "508", context.getString(R.string.c_Saint_Pierre_And_Miquelon), "Saint Pierre And Miquelon", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pn", "870", context.getString(R.string.c_Pitcairn), "Pitcairn", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pr", "1", context.getString(R.string.c_Puerto_Rico), "Puerto Rico", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ps", "970", context.getString(R.string.c_Palestine), "Palestine", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pt", "351", context.getString(R.string.c_Portugal), "Portugal", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("pw", "680", context.getString(R.string.c_Palau), "Palau", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("py", "595", context.getString(R.string.c_Paraguay), "Paraguay", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("qa", "974", context.getString(R.string.c_Qatar), "Qatar", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("re", "262", context.getString(R.string.c_Réunion), "Réunion", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ro", "40", context.getString(R.string.c_Romania), "Romania", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("rs", "381", context.getString(R.string.c_Serbia), "Serbia", DEFAULT_FLAG_RES, false));
//        countries.add(new CCPCountry("ru", "7", "Russian Federation", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("rw", "250", context.getString(R.string.c_Rwanda), "Rwanda", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sa", "966", context.getString(R.string.c_Saudi_Arabia), "Saudi Arabia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sb", "677", context.getString(R.string.c_Solomon_Islands), "Solomon Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sc", "248", context.getString(R.string.c_Seychelles), "Seychelles", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sd", "249", context.getString(R.string.c_Sudan), "Sudan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("se", "46", context.getString(R.string.c_Sweden), "Sweden", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sg", "65", context.getString(R.string.c_Singapore), "Singapore", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sh", "290", context.getString(R.string.c_Saint_Helena), "Saint Helena, Ascension And Tristan Da Cunha", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("si", "386", context.getString(R.string.c_Slovenia), "Slovenia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sk", "421", context.getString(R.string.c_Slovakia), "Slovakia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sl", "232", context.getString(R.string.c_Sierra_Leone), "Sierra Leone", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sm", "378", context.getString(R.string.c_San_Marino), "San Marino", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sn", "221", context.getString(R.string.c_Senegal), "Senegal", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("so", "252", context.getString(R.string.c_Somalia), "Somalia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sr", "597", context.getString(R.string.c_Suriname), "Suriname", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("st", "239", context.getString(R.string.c_Sao_Tome), "Sao Tome And Principe", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sv", "503", context.getString(R.string.c_El_Salvador), "El Salvador", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sx", "1721", context.getString(R.string.c_Sint_Maarten), "Sint Maarten", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sy", "963", context.getString(R.string.c_Syrian_Arab), "Syrian Arab Republic", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("sz", "268", context.getString(R.string.c_Swaziland), "Swaziland", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tc", "1649", context.getString(R.string.c_Turks_and_Caicos), "Turks and Caicos Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("td", "235", context.getString(R.string.c_Chad), "Chad", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tg", "228", context.getString(R.string.c_Togo), "Togo", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("th", "66", context.getString(R.string.c_Thailand), "Thailand", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tj", "992", context.getString(R.string.c_Tajikistan), "Tajikistan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tk", "690", context.getString(R.string.c_Tokelau), "Tokelau", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tl", "670", context.getString(R.string.c_Timor_leste), "Timor-leste", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tm", "993", context.getString(R.string.c_Turkmenistan), "Turkmenistan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tn", "216", context.getString(R.string.c_Tunisia), "Tunisia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("to", "676", context.getString(R.string.c_Tonga), "Tonga", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tr", "90", context.getString(R.string.c_Turkey), "Turkey", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tt", "868", context.getString(R.string.c_Trinidad), "Trinidad &amp; Tobago", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tv", "688", context.getString(R.string.c_Tuvalu), "Tuvalu", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tw", "886", context.getString(R.string.c_Taiwan), "Taiwan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("tz", "255", context.getString(R.string.c_Tanzania_United), "Tanzania, United Republic Of", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ua", "380", context.getString(R.string.c_Ukraine), "Ukraine", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ug", "256", context.getString(R.string.c_Uganda), "Uganda", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("us", "1", context.getString(R.string.c_United_States), "United States", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("uy", "598", context.getString(R.string.c_Uruguay), "Uruguay", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("uz", "998", context.getString(R.string.c_Uzbekistan), "Uzbekistan", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("va", "379", context.getString(R.string.c_Holy_See), "Holy See (vatican City State)", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("vc", "1784", context.getString(R.string.c_Saint_Vincent), "Saint Vincent &amp; The Grenadines", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ve", "58", context.getString(R.string.c_Venezuela), "Venezuela, Bolivarian Republic Of", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("vg", "1284", context.getString(R.string.c_British_Virgin), "British Virgin Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("vi", "1", context.getString(R.string.c_US_Virgin), "US Virgin Islands", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("vn", "84", context.getString(R.string.c_Viet_Nam), "Viet Nam", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("vu", "678", context.getString(R.string.c_Vanuatu), "Vanuatu", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("wf", "681", context.getString(R.string.c_Wallis_And_Futuna), "Wallis And Futuna", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ws", "685", context.getString(R.string.c_Samoa), "Samoa", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("xk", "383", context.getString(R.string.c_Kosovo), "Kosovo", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("ye", "967", context.getString(R.string.c_Yemen), "Yemen", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("yt", "262", context.getString(R.string.c_Mayotte), "Mayotte", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("za", "27", context.getString(R.string.c_South_Africa), "South Africa", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("zm", "260", context.getString(R.string.c_Zambia), "Zambia", DEFAULT_FLAG_RES, false));
        countries.add(new CCPCountry("zw", "263", context.getString(R.string.c_Zimbabwe), "Zimbabwe", DEFAULT_FLAG_RES, false));
        return countries;
    }

    String getEnglishName() {
        return englishName;
    }

    private void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    int getFlagID() {
        if (flagResID == -99) {
            flagResID = getFlagMasterResID(this);
        }
        return flagResID;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    String getNameCode() {
        return nameCode;
    }

    private void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    String getPhoneCode() {
        return phoneCode;
    }

    private void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void log() {
        try {
            Log.d(TAG, "Country->" + nameCode + ":" + phoneCode + ":" + name);
        } catch (NullPointerException ex) {
            Log.d(TAG, "Null");
        }
    }

    String logString() {
        return nameCode.toUpperCase() + " +" + phoneCode + "(" + name + ")";
    }

    /**
     * If country have query word in name or name code or phone code, this will return true.
     *
     * @param query
     * @return
     */
    boolean isEligibleForQuery(String query) {
        query = query.toLowerCase();
        return getName().toLowerCase().contains(query) || getNameCode().toLowerCase().contains(query) || getPhoneCode().toLowerCase().contains(query);
    }

    @Override
    public int compareTo(@NonNull CCPCountry o) {
        return Collator.getInstance().compare(getName(), o.getName());
    }
}
