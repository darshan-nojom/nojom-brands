package com.nojom.client.util;

import com.google.android.gms.wallet.WalletConstants;
import com.nojom.client.BuildConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public interface Constants {
    String API = "api/";

    String PRIVACY = "https://24task.com/privacy-policy";
    String TERMS_USE = "https://24task.com/terms-of-use";
    String FAQS = "https://24task.com/faqs";
    String ABOUTUS = "https://24task.com/about-us";

    String FACEBOOK_URL = "https://www.facebook.com/nojomApps";
    String GOOGLE_URL = "https://www.nojom.com";
    String TRUSTPILOT_URL = "https://www.trustpilot.com/evaluate/24task.com";
    String JABBER_URL = "https://www.sitejabber.com/online-business-review#24task.com_(24+Task+-)";
    String APPSTORE_URL = "https://apps.apple.com/us/app/nojom-app-for-brands/id1488448062";
    String PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=com.nojom.brands";
    String LINKEDIN_URL_ = "https://www.linkedin.com/company/nojomapp";
    String INSTAGRAM_URL_ = "https://instagram.com/nojomapp";
    String FACEBOOK_URL_ = "https://www.facebook.com/nojomApps";

    //NODE API
    //02-11-2020
    String BASE_URL = BuildConfig.DEBUG ? "https://client-stage-api.nojom.com/" : "https://client-prod-api.nojom.com/";
    String BASE_URL_GIG = BuildConfig.DEBUG ? "https://knecq9c098.execute-api.me-central-1.amazonaws.com/backend/" : "https://zap31dqqtk.execute-api.me-central-1.amazonaws.com/prod/";

    //LIVE URL
    String BASE_URL_CHAT_MSG = BuildConfig.DEBUG ? "https://chat-stage-api.nojom.com/" : "https://chat-prod-api.nojom.com/";
    String BASE_URL_CHAT = BuildConfig.DEBUG ? "https://chat-stage-api.nojom.com/api/" : "https://chat-prod-api.nojom.com/api/";

    int CLIENT_PROFILE = 2;
    int SYS_ID = 6;

    int BALANCE_ACCOUNT = 2;
    String TAB_BALANCE = "balance_tab";
    String AVAILABLE_BALANCE = "available_balance";
    String WITHDRAW_AMOUNT = "withdraw_amount";

    String CARD_ID = "card_id";
    int TAB_HOME = 0;
    int TAB_CHAT = 1;
    int TAB_FREE_TRIAL = 2;
    int TAB_POST_JOB = 2;
    int TAB_TASK_PRO = 2;
    int TAB_CALCULATE = 3;
    int TAB_JOB_LIST = 3;
    int MIN_PROJECT_AMOUNT = 9;//project amount should be minimum $9

    String SFTEXT_REGULAR = "font/sanfrancisco_text_regular.otf";
    String SFTEXT_BOLD = "font/sanfrancisco_text_bold.otf";
    String SF_PRO_TEXT_BOLD = "font/sf_pro_text_bold.otf";

    String REGISTRATION_COMPLETE = "registrationComplete";
    String FCM_TOKEN = "fcm_token";
    String JWT = "JWT";
    String FROM_LOGIN = "from login";
    String LOGIN_FINISH = "login_finish";
    String USER_DATA = "user_data";
    String USER_IMG_PATH = "user_img_path";
    String PROFILE_DATA = "profile_data";
    String AGENTS_DATA = "agents_data";
    String SOCIAL_GIG_DATA = "social_gig_data";
    String GIG_DATA = "gig_data";
    String AGENT_PROFILE_DATA = "agent_profile_data";
    String IS_LOGIN = "isLogin";
    String SCREEN_NAME = "screen_name";
    String IS_EDIT = "isEdit";
    String IS_FIXED_PRICE = "isFixedPrice";
    String PLATFORM_ID = "ServiceId";
    String PLATFORM_NAME = "platform_name";
    //    String IS_FROM_LAWYER_SCREEN = "isFromLawyerScreen";
    String SKILL_IDS = "SkillIds";
    String SKILL_NAMES = "SkillNames";
    String PAY_TYPE = "PayType";
    String CLIENT_RATE_ID = "ClientRateId";
    String CLIENT_RATE = "ClientRate";
    String BUDGET = "Budget";
    String DESCRIBE = "describe";
    String ATTACH_FILE = "AttachFile";
    String ATTACH_FILE_ID = "AttachFileIds";
    String ATTACH_LOCAL_FILE = "AttachLocalFile";
    String SHOW_HIRE = "ShowHire";
    String REHIRE = "Rehire";
    String APP_VERSION = "app_version";
    String ANDROID = "1";
    String IS_SOCIAL_LOGIN = "is_social_login";
    String ACCOUNT_DATA = "accountData";
    String ACCOUNT_ID = "account_id";
    String REFERRAL_ID_FROM_LINK = "refIdFromLink";
    String SELECTED_LANGUAGE = "selectedLanguage";
    String PREF_SELECTED_CURRENCY = "selectedCurrency";
    String PREF_IS_VERIFICATION = "is_verification";
    String LAWYER_CASE = "lawyerCase";
    String FROM_HOME = "fromHome";
    String COUNTRY_CODE_VALUE = "country_code";
    String IS_FROM_GIG = "isFromGig";
    String SELECTED_PACKAGE_POS = "selectedPackagePos";

    //Firebase Chat
    String CHAT_ID = "ChatID";
    String CHAT_DATA = "ChatData";
    String ID = "id";
    String SENDER_ID = "sender_id";
    String SENDER_NAME = "sender_name";
    String SENDER_PIC = "sender_pic";
    String RECEIVER_ID = "receiver_id";
    String RECEIVER_NAME = "receiver_name";
    String RECEIVER_PIC = "receiver_pic";
    String CHAT_OPEN_ID = "chatOpenScreenId";

    String PREF_SERVICES = "services";
    String PREF_TOP_SERVICES = "topServices";
    String PREF_TOP_CAT = "topCat";
    String PREF_LANGUAGE = "language";
    String PREF_SOCIAL_PLATFORMS = "socialPlatforms";
    String PREF_EXPERTS = "expertsUser";
    String SERVICE_NAME = "service_name";
    String SERVICE_TYPE = "service_type";
    String HIRE = "collaborate";
    String WHY_US = "whyus";
    String HOW_IT_WORKS = "howitworks";
    String IS_CLIENT_ACCOUNT = "isClientAccount";

    String WORK_IN_PROGRESS = "Work In Progress";
    String PAST_PROJECTS = "Past Projects";
    String IS_WORK_INPROGRESS = "isWorkInProgress";

    int CHOOSE_DEV_FRAGMENT_CODE = 102;
    int BUDGET_FRAGMENT_CODE = 103;
    int DEADLINE_FRAGMENT_CODE = 105;
    int ENTER_PRICE_FRAGMENT_CODE = 104;
    int CHOOSE_LAW_CAT_FRAGMENT_CODE = 108;

    String M_TYPE = "m_type";
    String M_PROJECTID = "project_id";
    String PROJECT_ID = "projectId";
    String PROJECT = "projects";
    String PROJECT_GIG = "projectsGig";
    String PROJECT_DETAIL = "projectsDetail";
    String EDIT_PROJECT_ID = "editProjectId";
    String DUPLICATE_PROJECT = "duplicateProject";
    String REPOST_PROJECT = "repostProject";

    int IN_PROGRESS = 2;
    int WAITING_FOR_DEPOSIT = 1;
    int WAITING_FOR_AGENT_ACCEPTANCE = 7;
    int BIDDING = 3;
    int SUBMIT_WAITING_FOR_PAYMENT = 8;
    int COMPLETED = 4;
    int CANCELLED = 5;
    int REFUNDED = 9;
    int REMOVED = 11;
    int UNDER_REVIEW = 12;
    int BANK_TRANSFER_REVIEW = 15;

    // Get Location
    String PICK_LOCATION_ADDRESS = "pickLocationAddress";
    String PICK_LOCATION_LATITUDE = "pickLocationLatitude";
    String PICK_LOCATION_LONGITUDE = "pickLocationLongitude";
    String ADD_DESCRIBE = "addDescribe";
    String CONTRACT_ID = "contractID";

    String KEY_FOR_MAINTENANCE = "Client24Task_Maintenance_Android";
    String KEY_FOR_APP_PROMO = "TaskClient_App_Promo";
    String KEY_FOR_GIG_SHOW = "isGigHideClient";

    String SORT_BY = "sort_by";
    String SERVICE_CATEGORY_ID = "service_category_id";
    String SERVICE_CATEGORY_NAME = "service_category_name";
    String LANGUAGE_ID = "langId";
    String WORKBASE = "workbase";
    String AVAILABILITY = "availability";
    String SKILL_ID = "skill_id";
    String AGENT_BANNER = "agent_bannner";

    // 0 - Basic
    // 1 - Conversational
    // 2 - Fluent
    // 3 - Native
    int BASIC_ID = 0;
    int CONVERSATIONAL_ID = 1;
    int FLUENT_ID = 2;
    int NATIVE_ID = 3;

    String BASIC = "Basic";
    String CONVERSATIONAL = "Conversational";
    String FLUENT = "Fluent";
    String NATIVE = "Native";

    // "Less than 1 year"
    // "1-3 years"
    // "4-6 years"
    // "7-9 years"
    // "10-12 years"
    // "13+ years"
    int LESS_THAN_1_ID = 0;
    int YEAR_1_3_ID = 1;
    int YEAR_4_6_ID = 2;
    int YEAR_7_9_ID = 3;
    int YEAR_10_12_ID = 4;
    int YEAR_13_15_ID = 5;
    int YEAR_16_18_ID = 6;
    int YEAR_19_21_ID = 7;
    int YEAR_22_24_ID = 8;
    int YEAR_25_27_ID = 9;
    int YEAR_28_30_ID = 10;
    int YEAR_31_ID = 11;

    String LESS_THAN_1 = "Less than 1 year";
    String YEAR_1_3 = "1-3 years";
    String YEAR_4_6 = "4-6 years";
    String YEAR_7_9 = "7-9 years";
    String YEAR_10_12 = "10-12 years";
    String YEAR_13_15 = "13-15 years";
    String YEAR_16_18 = "16-18 years";
    String YEAR_19_21 = "19-21 years";
    String YEAR_22_24 = "22-24 years";
    String YEAR_25_27 = "25-27 years";
    String YEAR_28_30 = "28-30 years";
    String YEAR_31_ = "31+ years";

    //0 - associate, 1 - bachelor, 2 - master, 3 - doctorate
    int Associate_ID = 0;
    int Bachelor_ID = 1;
    int Master_ID = 2;
    int Doctorate_ID = 3;

    String Associate = "Associate";
    String Bachelor = "Bachelor";
    String Master = "Master";
    String Doctorate = "Doctorate";

    //0 - Beginner 1 - Intermediate 2 - Expert
    int Beginner_ID = 0;
    int Intermediate_ID = 1;
    int Expert_ID = 2;

    String Beginner = "Beginner";
    String Intermediate = "Intermediate";
    String Expert = "Expert";

    String ADD_CARD_KEY = "add_card_key";
    int ADD_CARD_VAL = 1;

    String EDIT_CARD_KEY = "edit_card_key";
    int EDIT_CARD_VAL = 2;

    String CVV_VISIBLE_KEY = "cvv_key";
    int CVV_VISIBLE_VAL = 3;
    int CARD_REQUEST_CODE = 1111;
    int POSITION = 1234;

    String PREF_PARTNER_APP = "partner_app";
    String PREF_PARTNER_ABOUT = "partner_about";

    String KEY_FORCE_UPDATE_REQUIRED = "client_force_update";

    //stripe production and live key
    String STRIPE_KEY_PRODUCTION = "pk_live_51OxoqjEaF1L9bNKdXyBqAnWUT9SxrgzC5o6zjiAVBM3iizjVq33pfJYU3JjxdM77TcgQ5dZ7K94LyfZUM1XuGr0o00dQqqyyBA";
    String STRIPE_KEY_TEST = "pk_test_51OxoqjEaF1L9bNKdgNUC2LzIMornqHdb489qwYw7Y5gt9xMoYzvFTtQGJCqIi9UEV4XqvAU1ZiMvIhBwZLmbyZoD003hfLA1Ma";

    //merchant id
    String MERCHANT_ID = "15810006309264987485";

    int PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;

    List<String> SUPPORTED_NETWORKS = Arrays.asList("AMEX", "DISCOVER", "JCB", "MASTERCARD", "VISA");

    List<String> SUPPORTED_METHODS = Arrays.asList("PAN_ONLY", "CRYPTOGRAM_3DS");

    String COUNTRY_CODE = "US";

    String CURRENCY_CODE = "USD";

    List<String> SHIPPING_SUPPORTED_COUNTRIES = Arrays.asList("US", "GB", "IN");

    String PAYMENT_GATEWAY_TOKENIZATION_NAME = "Stripe";

    HashMap<String, String> PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS = new HashMap<String, String>() {
        {
            put("gateway", PAYMENT_GATEWAY_TOKENIZATION_NAME);
            put("gatewayMerchantId", "exampleGatewayMerchantId");
            // Your processor may require additional parameters.
        }
    };

    String DIRECT_TOKENIZATION_PUBLIC_KEY = "pk_test_n05ja72jeYH7zsKIZQL9q513";

    HashMap<String, String> DIRECT_TOKENIZATION_PARAMETERS = new HashMap<String, String>() {
        {
            put("protocolVersion", "ECv2");
            put("publicKey", DIRECT_TOKENIZATION_PUBLIC_KEY);
        }
    };

    String SELECT_FOLLOWER = "Select Followers";
    String FOLLOWER_1 = "1k-10k";
    String FOLLOWER_2 = "10k-50k";
    String FOLLOWER_3 = "50k-100k";
    String FOLLOWER_4 = "100k-500k";
    String FOLLOWER_5 = "500k-1M";
    String FOLLOWER_6 = "1M-10M";
    String FOLLOWER_7 = "10M+";

    String LOGIN_TYPE_SOCIAL = "social_media_login";
    String LOGIN_TYPE_NORMAL = "normal_login";

    //    String API_LOGIN = "devLogin";
    String API_LOGIN = "login";
    String API_REGISTER = "signup";
    String API_SERVICE_CATEGORIES = "getServiceCategories";
    String API_SERVICE_CATEGORIES_V2 = "getServiceCategories?v=2";
    String API_SOCIAL_PLATFORMS = "getSocialPlatforms";
    String API_GET_LANGUAGE = "getLanguage";
    String API_GET_EXPERT_INFO = "getExpertInfo";
    //03-11-2020
    String API_CHECK_PROMO_CODE = "checkPromocode";
    String API_GET_PORTFOLIO = "getPortfolio";
    String API_GET_AGENT_REVIEW = "getAgentReview";
    String API_GET_AGENT_PROFILE_SKILLS = "getAgentProfileSkills";//TODO: not usage
    String API_GET_PROFILE_INFO = "getAgentProfileInfo";
    String API_GET_CLIENT_RATES = "getClientRates";
    String API_GET_CLIENT_BALANCE = "getClientBalance";
    String API_GET_DEPOSIT = "getDeposit";//TODO: no usage
    String API_GET_HISTORY = "getHistory";
    String API_GET_SOCIAL_SURVEY = "getSocialSurvey";
    String API_SAVE_AGENT = "clientSaveFavAgent";
    String API_REMOVE_AGENT = "clientRemoveFavAgent";
    String API_RESET_PASS = "resetPassword";
    //04-11-2020
    String API_FORGET_PASS = "forgetPassword";
    String API_UPDATE_PASS = "updatePassword";
    String API_ADD_SURVEY = "addClientSurveys";
    String API_GET_SURVEY_DETAILS = "getSocialSurveyDetail";
    String API_DELETE_SURVEY_IMG = "deleteSurveysImg";
    String API_ADD_SOCIAL_SURVEY = "addSocialSurveys";
    String API_GET_NOTIFICATION_SETTINGS = "getNotificationSettings";
    String API_UPDATE_NOTIFICATION_SETTINGS = "updateNotificationStatus";
    String API_SEND_FEEDBACK = "sendFeedback";
    //05-11-2020
    String API_GET_CLIENT_PROFILE = "getClientProfileInfo";
    String API_GET_PROMO_CODE_HISTORY = "getPromocodeHistory";
    String API_GET_USER_LEVEL = "getUserLevel";
    String API_VERIFY_FACEBOOK = "verifyFacebook";//TODO: no usage
    String API_ADD_WITHDRAWAL_REQUEST = "addWithdrawalRequest";//TODO:Feature not implement yet [Refund Balance case]
    //06-11-2020
    String API_EDIT_PAYMENT_ACCOUNT = "editPaymentAccount";//i think there is no usage
    String API_DELETE_PAYMENT_ACCOUNT = "deletePaymentAccount";//i think there is no usage
    String API_SEND_VERIFICATION_LINK = "sendVerificationLink";//i think there is no usage
    String API_LOGOUT = "logout";
    //07-11-2020
//    String API_GET_JOB_POST = "getJobPosts";
    String API_GET_JOB_POST = "getJobPostsV2";
    //09-11-2020
    String API_BLOCK_USER = "addToBlockUser";
    String API_UNBLOCK_USER = "unblockUser";
    String API_EDIT_PRIMARY_ACCOUNT = "editPrimaryAccount";
    String API_SEND_EMAIL_VERIFICATION = "sendEmailVerification";
    //10-11-2020
    String API_JOB_DETAILS = "JobDetailsById?v=2";
    String API_ADD_AGENT_REVIEW = "addAgentReviews";
    String API_GET_REVIEW_QUESTION_LIST = "getQuestionsReviewList";
    String API_DELETE_JOB_POST = "deleteJobpost";
    //11-11-2020
    String API_JOB_POST_BIDLIST = "jobPostBidsList";
    //23-11-2020
    String API_ADD_JOB_POST = "addJobPost";
    String API_CLOSE_JOB_POST = "closeProject";
    String API_REFUND_JOB_POST = "addJobRefunds";
    String API_UPLOAD_JOb_ATTACHMENT = "uploadAttachmentForJobPost";
    String API_ADD_CARD = "addCard";
    String API_STRIPE_ADD_CARD = "addStripeCard";
    String API_DELETE_CARD = "deleteCard";
    String API_DELETE_STRIPE_CARD = "deleteStripeCard";
    String API_EDIT_CARD = "editCard";
    String API_STRIPE_EDIT_CARD = "editStripeCard";
    String API_CANCEL_CONTRACT = "cancelContracts";
    String API_UPDATE_PROFILE_PIC = "updateProfilePicture";
    String API_USER_WALLET_LIST = "userWalletLists";
    String API_GET_STRIPE_CARD_LIST = "getStripeCardList";
    String API_GENERATE_BRAINTREE_TOKEN = "generateBraintreeToken";
    //    String API_ADD_CONTRACT = "addContracts";
    String API_ADD_CONTRACT = "addContractsV2";
    String API_RELEASE_INVOICE = "releaseInvoice";
    String API_ADD_BALANCE = "addBraintreeBalance";
    //    String API_DO_BRAINTREE_PAYMENT = "doBraintreePaymentNew";
    String API_DO_BRAINTREE_PAYMENT = "doBraintreePayment";
    String API_DO_BANK_TRANSFER = "doBankPayment";
    String API_DO_BANK_TRANSFER_GIG = BASE_URL_GIG + "client/purchaseCustomGigByBank";
    String API_GET_BANKS = "getBanks";
    String API_DO_STRIPE_PAYMENT = "doStripePayment";
    String API_UPDATE_PROFILE = "updateProfile";
    //24-11-2020
    String API_EDIT_JOB_POST = "editJobPost";
    //25-11-2020
    String API_VERIFY_PAYPAL_PAYMENT = "verifyPaypalPayment";
    String API_PROFILE_VERIFICATIONS = "addProfileVerification";
    String API_DOWNLOAD_FILE = "fileDownloads?";
    //28-11-2020
    String API_CLIENT_FEEDBACK_REVIEW = "getFeedbackOfClientReviews";
    //05-01-2021
    String API_UPDATE_USERNAME = "updateUsername";
    //23-01-2021
    String API_GET_ALL_COUNTRIES = "getAllCountries";
    String API_GET_ALL_CITY = "getCityByCountry/";
    String API_GET_STATE = "getStatesByCountry";
    String API_GET_CITIES = "getCityByState";
    // Gig API
    //18-02-2021
//    String API_GET_GIG_LISTS = BASE_URL_GIG + "dev/client/getGigLists";
    String API_GET_SOCIAL_INFLUENCE_PROFILES = BASE_URL_GIG + "client/getSocialInfluenceProfiles";//TODO: no usage
    String API_SAVE_GIG = BASE_URL_GIG + "client/saveFavGig";
    String API_REMOVE_GIG = BASE_URL_GIG + "client/removeFavGig";
    //07-06-2021 changes in 09-10-2021
    String API_GET_CUSTOM_GIG_DETAILS = BASE_URL_GIG + "client/viewGigDetails";
    //26-02-2021
//    String API_DO_GIGS_PAYMENT = BASE_URL_GIG + "dev/client/doGigsPayment";
    String API_DO_GIGS_PAYMENT = BASE_URL_GIG + "client/doGigsPaymentV2";
    String API_DO_CUSTOM_GIGS_PAYMENT = BASE_URL_GIG + "client/purchaseCustomGig";
    String API_DO_CUSTOM_GIGS_STRIPE_PAYMENT = BASE_URL_GIG + "client/purchaseCustomGigByStripe";
    String API_DO_GIGS_STRIPE_PACKAGE_PAYMENT = BASE_URL_GIG + "client/doPackageGigsPaymentByStripe";
    //04-03-2021
    String API_GET_CONTRACT_DETAILS = BASE_URL_GIG + "client/getContractDetails";
    String API_GET_CUSTOM_CONTRACT_DETAILS = BASE_URL_GIG + "client/getCustomContractDetails";
    //05-03-2021
    String API_ADD_EDIT_CLIENT_JOB_DESCRIBE = BASE_URL_GIG + "client/addEditClientJobDescribe";
    String API_UPLOAD_CLIENT_CONTRACT_ATTACHMENT = BASE_URL_GIG + "client/uploadClientContractAttachment";
    String API_REMOVE_CLIENT_CONTRACT_ATTACHMENT = BASE_URL_GIG + "client/removeClientContractAttachment";
    String API_GET_FEED_BACK_LISTS = BASE_URL_GIG + "gigs/getFeedbackLists";
    String API_ADD_FEED_BACK = BASE_URL_GIG + "client/addFeedback";
    //08-03-2021
    String API_ADD_JOB_REFUNDS = BASE_URL_GIG + "client/addJobRefunds";
    String API_RELEASE_GIGS_PAYMENT = BASE_URL_GIG + "client/releaseGigsPayment";
    String API_GET_PARTNER_QUESTION = "getPartnerApplicationQuestions";
    String API_ADD_PARTNER_ANSWER = "addPartnerApplicationAnswers";
    String API_ADD_PARTNER_ABOUT_ANSWER = "addPartnerAboutAnswers";
    //05-04-2021
    String API_ALL_SUB_SERVICES = "getAllSubServices";
    //14-05-2021
    String API_SET_COORDINATES = "setCoordinates";
    //03-06-2021
    String API_GET_PAYMENTMETHOD = "getPaymentMethod";
    String API_GET_AGENT_BY_USER_NAME = "getAgentByUsername";//TODO: no usage
    String API_GET_DELETE_PROFILE = "deleteProfile";
    String API_GET_ACCEPT_OR_REJECT_OFFER = BASE_URL_GIG + "client/acceptOrRejectOffer";
    String API_GET_VIEW_OFFER_DETAILS = BASE_URL_GIG + "client/viewOfferDetails";
    String API_GET_SOCIAL_GIGS = BASE_URL_GIG + "client/getAllSocialGigLists";//TODO: no usage

    String API_GET_SOCIAL_PLATFORM_LIST = BASE_URL_GIG + "client/getSocialPlatformGigListsByID/";

    String API_GET_AGENCY = BASE_URL + "getAgentProfileAgency/";
    String API_SAVE_INFLU = BASE_URL + "addFavInfluencer";//TODO: no usage
    String API_SEARCH_INFLU = BASE_URL + "getListInfluenceProfiles?v=2";

    String API_ADD_CRN = "addCommercialRegistration";
    String API_ADD_VAT = "addVatRegistration";
    String API_DELETE_CRN = "deleteCommercialRegistration";
    String API_DELETE_VAT = "deleteVatRegistration";
    String API_GET_SKILL = "getSkills?v=2";

    String API_GET_AGENT_COMPANIES = "getAgentCompanies";
    String API_GET_AGENT_STORES = "getAgentStore";
    String API_GET_AGENT_PRODUCT = "getAgentProducts";
    String API_GET_AGENT_PARTNERS = "getAgentPartners";
    String API_GET_AGENT_YOUTUBE = "getAgentYoutube";
    String API_ADD_SERVICE = "services";

    String API_CAMP_ATTACH = "campaign/attachment";
    String API_CREATE_CAMP = "campaign";
    String API_CREATE_PAYMENT = "campaign/payment/";
    String API_FETCH_CAMPAIGN = "campaigns/merged?page=";
    String API_MY_INVOICES = "invoices?page=";
    String API_INVOICES_REPORT = BuildConfig.DEBUG ? "http://ec2-15-184-217-235.me-south-1.compute.amazonaws.com:4000/api/invoice/report/" : "http://ec2-15-184-217-235.me-south-1.compute.amazonaws.com:3000/api/invoice/report/";

    String API_CONTACT_UNIQUE = "checkContactsUniqueness";
    String API_SEND_CODE = "sendOtpPhone";
    String API_VERIFY_CODE = "verifyOtpPhone";
    String API_AGENT_SERVICE = "campaign/agents?page_no=";
    String API_GET_WALLET = "wallet";
    String API_GET_WALLET_TXN = "transactions?limit=1000";
    String API_CHARGE_WALLET = "charge-wallet";
    String API_GET_RATES = "sys/settings/rates";
    String API_GET_AGENTS = "campaign/agents?limit=30&page_no=";
    String API_GET_CURRENT_ORDERS = "campaigns?limit=20&campaign_status=pending&campaign_status=in_progress&campaign_status=completed&released=false&page=";
    String API_GET_PAST_ORDERS = "campaigns?limit=20&campaign_status=released&campaign_status=canceled&page=";
    String API_UPDATE_NOTIFICATION = "updateNotificationsEnabled";
}
