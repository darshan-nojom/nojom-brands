package com.nojom.client;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.nojom.client.databinding.ActivityAccountDeleteBindingImpl;
import com.nojom.client.databinding.ActivityAddCardBindingImpl;
import com.nojom.client.databinding.ActivityAddDescribeBindingImpl;
import com.nojom.client.databinding.ActivityAddMoneyBindingImpl;
import com.nojom.client.databinding.ActivityAddPaypalBindingImpl;
import com.nojom.client.databinding.ActivityAddStarBindingImpl;
import com.nojom.client.databinding.ActivityAddSurveyBindingImpl;
import com.nojom.client.databinding.ActivityAddSurveySubmitBindingImpl;
import com.nojom.client.databinding.ActivityAutoPalceLocationBindingImpl;
import com.nojom.client.databinding.ActivityBalanceBindingImpl;
import com.nojom.client.databinding.ActivityBankTransferBindingImpl;
import com.nojom.client.databinding.ActivityCallerBindingImpl;
import com.nojom.client.databinding.ActivityCampDataBindingImpl;
import com.nojom.client.databinding.ActivityCampDetailsBindingImpl;
import com.nojom.client.databinding.ActivityCampStarsBindingImpl;
import com.nojom.client.databinding.ActivityCampaignDetailsBindingImpl;
import com.nojom.client.databinding.ActivityChatBindingImpl;
import com.nojom.client.databinding.ActivityChatMessagesBindingImpl;
import com.nojom.client.databinding.ActivityChooseAccountBindingImpl;
import com.nojom.client.databinding.ActivityChoosePaymentMethodBindingImpl;
import com.nojom.client.databinding.ActivityClientMoreBindingImpl;
import com.nojom.client.databinding.ActivityClientProfileBindingImpl;
import com.nojom.client.databinding.ActivityClientReviewBindingImpl;
import com.nojom.client.databinding.ActivityClientSettingBindingImpl;
import com.nojom.client.databinding.ActivityClientSettingsBindingImpl;
import com.nojom.client.databinding.ActivityDepositBindingImpl;
import com.nojom.client.databinding.ActivityDepositFundsCopyBindingImpl;
import com.nojom.client.databinding.ActivityEditPaypalBindingImpl;
import com.nojom.client.databinding.ActivityEmailVerifyBindingImpl;
import com.nojom.client.databinding.ActivityExpertFilterBindingImpl;
import com.nojom.client.databinding.ActivityExpertGigFilterBindingImpl;
import com.nojom.client.databinding.ActivityFindExpertBindingImpl;
import com.nojom.client.databinding.ActivityFreelancerProfileBindingImpl;
import com.nojom.client.databinding.ActivityGetDiscountBindingImpl;
import com.nojom.client.databinding.ActivityGigDetailsBindingImpl;
import com.nojom.client.databinding.ActivityGigExtrasBindingImpl;
import com.nojom.client.databinding.ActivityHireBindingImpl;
import com.nojom.client.databinding.ActivityHomeBindingImpl;
import com.nojom.client.databinding.ActivityHowItWorksBindingImpl;
import com.nojom.client.databinding.ActivityInfServiceBindingImpl;
import com.nojom.client.databinding.ActivityInflProfileAllBindingImpl;
import com.nojom.client.databinding.ActivityInfluencerFilterBindingImpl;
import com.nojom.client.databinding.ActivityInfluencerProfileBindingImpl;
import com.nojom.client.databinding.ActivityInfluencerProfileCopyBindingImpl;
import com.nojom.client.databinding.ActivityJobPostTitleBindingImpl;
import com.nojom.client.databinding.ActivityLawyerHomeBindingImpl;
import com.nojom.client.databinding.ActivityLawyerHomeNewBindingImpl;
import com.nojom.client.databinding.ActivityLoginSignUpBindingImpl;
import com.nojom.client.databinding.ActivityMainBindingImpl;
import com.nojom.client.databinding.ActivityMaintainanceBindingImpl;
import com.nojom.client.databinding.ActivityMilestoneBindingImpl;
import com.nojom.client.databinding.ActivityMyCampaignBindingImpl;
import com.nojom.client.databinding.ActivityMyInvoicesBindingImpl;
import com.nojom.client.databinding.ActivityMyOrdersBindingImpl;
import com.nojom.client.databinding.ActivityMyProfileBindingImpl;
import com.nojom.client.databinding.ActivityMyProjectsBindingImpl;
import com.nojom.client.databinding.ActivityNewPolicyBindingImpl;
import com.nojom.client.databinding.ActivityNotificationBindingImpl;
import com.nojom.client.databinding.ActivityOfferSummaryBindingImpl;
import com.nojom.client.databinding.ActivityOfferTitleBindingImpl;
import com.nojom.client.databinding.ActivityOrderDetailsBindingImpl;
import com.nojom.client.databinding.ActivityOtpBindingImpl;
import com.nojom.client.databinding.ActivityOtpVerifyBindingImpl;
import com.nojom.client.databinding.ActivityPartnerAboutBindingImpl;
import com.nojom.client.databinding.ActivityPartnerProfileBindingImpl;
import com.nojom.client.databinding.ActivityPartnerWithUsBindingImpl;
import com.nojom.client.databinding.ActivityPaymentBindingImpl;
import com.nojom.client.databinding.ActivityPaymentNewBindingImpl;
import com.nojom.client.databinding.ActivityPhoneVerifyBindingImpl;
import com.nojom.client.databinding.ActivityPostJobBindingImpl;
import com.nojom.client.databinding.ActivityPostJobNewBindingImpl;
import com.nojom.client.databinding.ActivityPrivateInfoBindingImpl;
import com.nojom.client.databinding.ActivityProfileInfoBindingImpl;
import com.nojom.client.databinding.ActivityProfileStarsBindingImpl;
import com.nojom.client.databinding.ActivityProjectDetailsBindingImpl;
import com.nojom.client.databinding.ActivityPublicProfileBindingImpl;
import com.nojom.client.databinding.ActivitySearchTagsBindingImpl;
import com.nojom.client.databinding.ActivitySelectAccountBindingImpl;
import com.nojom.client.databinding.ActivitySelectExpertiseBindingImpl;
import com.nojom.client.databinding.ActivitySelectFreelancerBindingImpl;
import com.nojom.client.databinding.ActivitySellersServiceBindingImpl;
import com.nojom.client.databinding.ActivityServiceBindingImpl;
import com.nojom.client.databinding.ActivityServiceSellersSearchBindingImpl;
import com.nojom.client.databinding.ActivityServicesBindingImpl;
import com.nojom.client.databinding.ActivityShareDiscountBindingImpl;
import com.nojom.client.databinding.ActivitySplashBindingImpl;
import com.nojom.client.databinding.ActivityUpdateLocationBindingImpl;
import com.nojom.client.databinding.ActivityUpdatePasswordBindingImpl;
import com.nojom.client.databinding.ActivityUsernameBindingImpl;
import com.nojom.client.databinding.ActivityVerificationBindingImpl;
import com.nojom.client.databinding.ActivityVerifyPaymentBindingImpl;
import com.nojom.client.databinding.ActivityViewAllGigBindingImpl;
import com.nojom.client.databinding.ActivityWalletAddBalanceBindingImpl;
import com.nojom.client.databinding.ActivityWalletBindingImpl;
import com.nojom.client.databinding.ActivityWebViewBindingImpl;
import com.nojom.client.databinding.ActivityWhatWeDoBindingImpl;
import com.nojom.client.databinding.ActivityWithdrawMoneyBindingImpl;
import com.nojom.client.databinding.ChatMoreOptionsBindingImpl;
import com.nojom.client.databinding.ChipBindingImpl;
import com.nojom.client.databinding.ContentAddcardBindingImpl;
import com.nojom.client.databinding.ContentCardListBindingImpl;
import com.nojom.client.databinding.CustomDialogBindingImpl;
import com.nojom.client.databinding.CustomPartnerAboutViewBindingImpl;
import com.nojom.client.databinding.CustomPartnerTextviewBindingImpl;
import com.nojom.client.databinding.CustomToastBindingImpl;
import com.nojom.client.databinding.DialogAddContactBindingImpl;
import com.nojom.client.databinding.DialogBalanceDepositBindingImpl;
import com.nojom.client.databinding.DialogBankTransferDoneBindingImpl;
import com.nojom.client.databinding.DialogCameraDocumentSelectBindingImpl;
import com.nojom.client.databinding.DialogCancelFreelancerBindingImpl;
import com.nojom.client.databinding.DialogChatNowBindingImpl;
import com.nojom.client.databinding.DialogChatSettingBindingImpl;
import com.nojom.client.databinding.DialogChoosePlatformBindingImpl;
import com.nojom.client.databinding.DialogCloseProjectBindingImpl;
import com.nojom.client.databinding.DialogCurrencyBindingImpl;
import com.nojom.client.databinding.DialogCustomPriceBindingImpl;
import com.nojom.client.databinding.DialogCustomPriceOptionBindingImpl;
import com.nojom.client.databinding.DialogDeleteAccountBindingImpl;
import com.nojom.client.databinding.DialogDeleteProjectBindingImpl;
import com.nojom.client.databinding.DialogDepositBindingImpl;
import com.nojom.client.databinding.DialogDepositNotesBindingImpl;
import com.nojom.client.databinding.DialogEmailVerificationBindingImpl;
import com.nojom.client.databinding.DialogEnterCvvBindingImpl;
import com.nojom.client.databinding.DialogFeedbackBindingImpl;
import com.nojom.client.databinding.DialogFileOpenBindingImpl;
import com.nojom.client.databinding.DialogFileOptionMenuBindingImpl;
import com.nojom.client.databinding.DialogForgotPasswordBindingImpl;
import com.nojom.client.databinding.DialogFreeTrialBindingImpl;
import com.nojom.client.databinding.DialogItemSelectBlackBindingImpl;
import com.nojom.client.databinding.DialogLanguageBindingImpl;
import com.nojom.client.databinding.DialogLocationDisclosureBindingImpl;
import com.nojom.client.databinding.DialogLocationSkillBindingImpl;
import com.nojom.client.databinding.DialogLoginNewBindingImpl;
import com.nojom.client.databinding.DialogLoginSignUpBindingImpl;
import com.nojom.client.databinding.DialogLogoutBindingImpl;
import com.nojom.client.databinding.DialogMyProfileDetailBindingImpl;
import com.nojom.client.databinding.DialogNoInternetBindingImpl;
import com.nojom.client.databinding.DialogOpenWebsiteBindingImpl;
import com.nojom.client.databinding.DialogPayDoneBindingImpl;
import com.nojom.client.databinding.DialogPaymentDepositBindingImpl;
import com.nojom.client.databinding.DialogPaymentDepositDoneBindingImpl;
import com.nojom.client.databinding.DialogPaymentDoneBindingImpl;
import com.nojom.client.databinding.DialogPostingDoneBindingImpl;
import com.nojom.client.databinding.DialogPromoCodeBindingImpl;
import com.nojom.client.databinding.DialogRateAppBindingImpl;
import com.nojom.client.databinding.DialogRateMeBindingImpl;
import com.nojom.client.databinding.DialogRefundPaymentBindingImpl;
import com.nojom.client.databinding.DialogRefundReasonBindingImpl;
import com.nojom.client.databinding.DialogRefundUserBindingImpl;
import com.nojom.client.databinding.DialogReleasePaymentBindingImpl;
import com.nojom.client.databinding.DialogRepostDeleteJobBindingImpl;
import com.nojom.client.databinding.DialogSecurityCodeBindingImpl;
import com.nojom.client.databinding.DialogSortByFilterBindingImpl;
import com.nojom.client.databinding.DialogStarsBindingImpl;
import com.nojom.client.databinding.DialogStorageDisclosureBindingImpl;
import com.nojom.client.databinding.DialogTimelineBindingImpl;
import com.nojom.client.databinding.DialogWithdrawBindingImpl;
import com.nojom.client.databinding.DialogZoomPortfolioImageBindingImpl;
import com.nojom.client.databinding.FragmentAboutProfileBindingImpl;
import com.nojom.client.databinding.FragmentAllPopularLawyerBindingImpl;
import com.nojom.client.databinding.FragmentAttachmentBindingImpl;
import com.nojom.client.databinding.FragmentBalanceDepositBindingImpl;
import com.nojom.client.databinding.FragmentBalanceHistoryBindingImpl;
import com.nojom.client.databinding.FragmentBalancePaymentBindingImpl;
import com.nojom.client.databinding.FragmentCampDetailBindingImpl;
import com.nojom.client.databinding.FragmentCampPayBindingImpl;
import com.nojom.client.databinding.FragmentCampStarsBindingImpl;
import com.nojom.client.databinding.FragmentChatListBindingImpl;
import com.nojom.client.databinding.FragmentChatManagerBindingImpl;
import com.nojom.client.databinding.FragmentChooseDeveloperBindingImpl;
import com.nojom.client.databinding.FragmentChooseSkillsBindingImpl;
import com.nojom.client.databinding.FragmentDeadlineBindingImpl;
import com.nojom.client.databinding.FragmentDepositFundsBindingImpl;
import com.nojom.client.databinding.FragmentDescribeBindingImpl;
import com.nojom.client.databinding.FragmentEarnMoneyBindingImpl;
import com.nojom.client.databinding.FragmentEnterRateBindingImpl;
import com.nojom.client.databinding.FragmentInfAgencyBindingImpl;
import com.nojom.client.databinding.FragmentInfAllBindingImpl;
import com.nojom.client.databinding.FragmentInfServicesBindingImpl;
import com.nojom.client.databinding.FragmentLawyerServiceBindingImpl;
import com.nojom.client.databinding.FragmentLiveChatBindingImpl;
import com.nojom.client.databinding.FragmentMyLevelBindingImpl;
import com.nojom.client.databinding.FragmentPayTypeBindingImpl;
import com.nojom.client.databinding.FragmentPostJobBindingImpl;
import com.nojom.client.databinding.FragmentProjectDetailsBindingImpl;
import com.nojom.client.databinding.FragmentProjectPaymentNewBindingImpl;
import com.nojom.client.databinding.FragmentProjectRateBindingImpl;
import com.nojom.client.databinding.FragmentProjectStatusBindingImpl;
import com.nojom.client.databinding.FragmentProjectSubmitBindingImpl;
import com.nojom.client.databinding.FragmentProjectsListBindingImpl;
import com.nojom.client.databinding.FragmentReviewsProfileBindingImpl;
import com.nojom.client.databinding.FragmentSelectRateBindingImpl;
import com.nojom.client.databinding.FragmentSelectServiceBindingImpl;
import com.nojom.client.databinding.FragmentSkillProfileBindingImpl;
import com.nojom.client.databinding.FragmentWantToPayBindingImpl;
import com.nojom.client.databinding.FragmentWinBindingImpl;
import com.nojom.client.databinding.HireGridItemBindingImpl;
import com.nojom.client.databinding.HomeGridItemBindingImpl;
import com.nojom.client.databinding.HomeListItemBindingImpl;
import com.nojom.client.databinding.HomePageItemBindingImpl;
import com.nojom.client.databinding.ItemAccountBindingImpl;
import com.nojom.client.databinding.ItemBannerLawyerBindingImpl;
import com.nojom.client.databinding.ItemCampaignInprogressBindingImpl;
import com.nojom.client.databinding.ItemCampaignStarsBindingImpl;
import com.nojom.client.databinding.ItemCardListBindingImpl;
import com.nojom.client.databinding.ItemCardViewBindingImpl;
import com.nojom.client.databinding.ItemCategoryListBindingImpl;
import com.nojom.client.databinding.ItemChatListBindingImpl;
import com.nojom.client.databinding.ItemChatMessagesBindingImpl;
import com.nojom.client.databinding.ItemChatMsgBindingImpl;
import com.nojom.client.databinding.ItemChipViewBindingImpl;
import com.nojom.client.databinding.ItemChooseAccountBindingImpl;
import com.nojom.client.databinding.ItemClientReviewBindingImpl;
import com.nojom.client.databinding.ItemCustomGigDetailsBindingImpl;
import com.nojom.client.databinding.ItemCustomGigProjectDetailsBindingImpl;
import com.nojom.client.databinding.ItemCustomPriceBindingImpl;
import com.nojom.client.databinding.ItemExpertListBindingImpl;
import com.nojom.client.databinding.ItemExpertListPlaceholderBindingImpl;
import com.nojom.client.databinding.ItemFilesDescBindingImpl;
import com.nojom.client.databinding.ItemFilterServiceBindingImpl;
import com.nojom.client.databinding.ItemFollwersListBindingImpl;
import com.nojom.client.databinding.ItemGigDetailPagerBindingImpl;
import com.nojom.client.databinding.ItemGigDetailsBindingImpl;
import com.nojom.client.databinding.ItemGigDetailsPlaceholderBindingImpl;
import com.nojom.client.databinding.ItemGigExtrasBindingImpl;
import com.nojom.client.databinding.ItemGigHomeBindingImpl;
import com.nojom.client.databinding.ItemGigHomeNewBindingImpl;
import com.nojom.client.databinding.ItemGigPlaceholderBindingImpl;
import com.nojom.client.databinding.ItemHireBindingImpl;
import com.nojom.client.databinding.ItemHomeBindingImpl;
import com.nojom.client.databinding.ItemHomeCategoryBindingImpl;
import com.nojom.client.databinding.ItemHomeCategoryLawyerBindingImpl;
import com.nojom.client.databinding.ItemHomeFollowerBindingImpl;
import com.nojom.client.databinding.ItemHomeIsTopBindingImpl;
import com.nojom.client.databinding.ItemIncomeBalaneBindingImpl;
import com.nojom.client.databinding.ItemInfReviewsBindingImpl;
import com.nojom.client.databinding.ItemInfServiceBindingImpl;
import com.nojom.client.databinding.ItemInfServiceNewBindingImpl;
import com.nojom.client.databinding.ItemInfServicesBindingImpl;
import com.nojom.client.databinding.ItemInfStoreBindingImpl;
import com.nojom.client.databinding.ItemJobTitleBindingImpl;
import com.nojom.client.databinding.ItemLanguageAgentsBindingImpl;
import com.nojom.client.databinding.ItemListFilesBindingImpl;
import com.nojom.client.databinding.ItemListFilesSurveyBindingImpl;
import com.nojom.client.databinding.ItemMyInvoiceBindingImpl;
import com.nojom.client.databinding.ItemMyStoreBindingImpl;
import com.nojom.client.databinding.ItemNotificationBindingImpl;
import com.nojom.client.databinding.ItemNotificationPhBindingImpl;
import com.nojom.client.databinding.ItemOrderStarsBindingImpl;
import com.nojom.client.databinding.ItemOrdersBindingImpl;
import com.nojom.client.databinding.ItemOrdersPhBindingImpl;
import com.nojom.client.databinding.ItemOrdersTextBindingImpl;
import com.nojom.client.databinding.ItemPartnerAnswerBindingImpl;
import com.nojom.client.databinding.ItemPartnersBindingImpl;
import com.nojom.client.databinding.ItemPlatformBindingImpl;
import com.nojom.client.databinding.ItemPlatformImgBindingImpl;
import com.nojom.client.databinding.ItemPlatformSelectionBindingImpl;
import com.nojom.client.databinding.ItemPolicyBindingImpl;
import com.nojom.client.databinding.ItemPopularBindingImpl;
import com.nojom.client.databinding.ItemPopularPlaceholderBindingImpl;
import com.nojom.client.databinding.ItemPortfolioListBindingImpl;
import com.nojom.client.databinding.ItemPortfolioListPhBindingImpl;
import com.nojom.client.databinding.ItemPortfolioSmallBindingImpl;
import com.nojom.client.databinding.ItemProfileProductBindingImpl;
import com.nojom.client.databinding.ItemProjectsListBindingImpl;
import com.nojom.client.databinding.ItemProposalFreelancerListBindingImpl;
import com.nojom.client.databinding.ItemProposalListBindingImpl;
import com.nojom.client.databinding.ItemReferralHistoryBindingImpl;
import com.nojom.client.databinding.ItemReviewsBindingImpl;
import com.nojom.client.databinding.ItemReviewsPhBindingImpl;
import com.nojom.client.databinding.ItemSelectFullBindingImpl;
import com.nojom.client.databinding.ItemSelectLanguageBindingImpl;
import com.nojom.client.databinding.ItemSelectRateBindingImpl;
import com.nojom.client.databinding.ItemSelectServiceBindingImpl;
import com.nojom.client.databinding.ItemSelectedStarsBindingImpl;
import com.nojom.client.databinding.ItemServiceBindingImpl;
import com.nojom.client.databinding.ItemServicePlatformBindingImpl;
import com.nojom.client.databinding.ItemServiceSellersBindingImpl;
import com.nojom.client.databinding.ItemSinglemultiselectionBindingImpl;
import com.nojom.client.databinding.ItemSkillFilterBindingImpl;
import com.nojom.client.databinding.ItemSkillsBindingImpl;
import com.nojom.client.databinding.ItemSkillsEditBindingImpl;
import com.nojom.client.databinding.ItemSkillsPostBindingImpl;
import com.nojom.client.databinding.ItemSmBindingImpl;
import com.nojom.client.databinding.ItemSocialBindingImpl;
import com.nojom.client.databinding.ItemSocialGigBindingImpl;
import com.nojom.client.databinding.ItemSocialGigDetailsBindingImpl;
import com.nojom.client.databinding.ItemSocialInfServBindingImpl;
import com.nojom.client.databinding.ItemSocialMediaBindingImpl;
import com.nojom.client.databinding.ItemSocialMediaProfileBindingImpl;
import com.nojom.client.databinding.ItemSocialServiceBindingImpl;
import com.nojom.client.databinding.ItemStarsBindingImpl;
import com.nojom.client.databinding.ItemStarsPlatformBindingImpl;
import com.nojom.client.databinding.ItemSubmittedFilesBindingImpl;
import com.nojom.client.databinding.ItemTagViewBindingImpl;
import com.nojom.client.databinding.ItemUploadedFilesBindingImpl;
import com.nojom.client.databinding.ItemUserRehireBindingImpl;
import com.nojom.client.databinding.ItemVerificationBindingImpl;
import com.nojom.client.databinding.ItemVerifiedWithBindingImpl;
import com.nojom.client.databinding.ItemViewAllCategoryBindingImpl;
import com.nojom.client.databinding.ItemWalletBindingImpl;
import com.nojom.client.databinding.ItemWorkwithBindingImpl;
import com.nojom.client.databinding.ItemYoutubeProfileBindingImpl;
import com.nojom.client.databinding.LayImageBindingImpl;
import com.nojom.client.databinding.LayTextBindingImpl;
import com.nojom.client.databinding.LayoutHomeHowWorkBindingImpl;
import com.nojom.client.databinding.LayoutMilestonePaymentBindingImpl;
import com.nojom.client.databinding.LayoutTextviewBindingImpl;
import com.nojom.client.databinding.NoDataLayoutBindingImpl;
import com.nojom.client.databinding.TimelineItemBindingImpl;
import com.nojom.client.databinding.ToolbarBackBindingImpl;
import com.nojom.client.databinding.ToolbarOBindingImpl;
import com.nojom.client.databinding.ToolbarProgressNextBindingImpl;
import com.nojom.client.databinding.ToolbarSaveBindingImpl;
import com.nojom.client.databinding.ToolbarTitleBindingImpl;
import com.nojom.client.databinding.ViewAgencyBindingImpl;
import com.nojom.client.databinding.ViewMyStoreBindingImpl;
import com.nojom.client.databinding.ViewOverviewBindingImpl;
import com.nojom.client.databinding.ViewPartnerBindingImpl;
import com.nojom.client.databinding.ViewPortfolioBindingImpl;
import com.nojom.client.databinding.ViewServicesBindingImpl;
import com.nojom.client.databinding.ViewSocialMediaBindingImpl;
import com.nojom.client.databinding.ViewWorkwithBindingImpl;
import com.nojom.client.databinding.ViewYoutubeBindingImpl;
import com.nojom.client.databinding.VwLayoutItemAudioPickBindingImpl;
import com.nojom.client.databinding.VwLayoutItemFolderListBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYACCOUNTDELETE = 1;

  private static final int LAYOUT_ACTIVITYADDCARD = 2;

  private static final int LAYOUT_ACTIVITYADDDESCRIBE = 3;

  private static final int LAYOUT_ACTIVITYADDMONEY = 4;

  private static final int LAYOUT_ACTIVITYADDPAYPAL = 5;

  private static final int LAYOUT_ACTIVITYADDSTAR = 6;

  private static final int LAYOUT_ACTIVITYADDSURVEY = 7;

  private static final int LAYOUT_ACTIVITYADDSURVEYSUBMIT = 8;

  private static final int LAYOUT_ACTIVITYAUTOPALCELOCATION = 9;

  private static final int LAYOUT_ACTIVITYBALANCE = 10;

  private static final int LAYOUT_ACTIVITYBANKTRANSFER = 11;

  private static final int LAYOUT_ACTIVITYCALLER = 12;

  private static final int LAYOUT_ACTIVITYCAMPDATA = 13;

  private static final int LAYOUT_ACTIVITYCAMPDETAILS = 14;

  private static final int LAYOUT_ACTIVITYCAMPSTARS = 15;

  private static final int LAYOUT_ACTIVITYCAMPAIGNDETAILS = 16;

  private static final int LAYOUT_ACTIVITYCHAT = 17;

  private static final int LAYOUT_ACTIVITYCHATMESSAGES = 18;

  private static final int LAYOUT_ACTIVITYCHOOSEACCOUNT = 19;

  private static final int LAYOUT_ACTIVITYCHOOSEPAYMENTMETHOD = 20;

  private static final int LAYOUT_ACTIVITYCLIENTMORE = 21;

  private static final int LAYOUT_ACTIVITYCLIENTPROFILE = 22;

  private static final int LAYOUT_ACTIVITYCLIENTREVIEW = 23;

  private static final int LAYOUT_ACTIVITYCLIENTSETTING = 24;

  private static final int LAYOUT_ACTIVITYCLIENTSETTINGS = 25;

  private static final int LAYOUT_ACTIVITYDEPOSIT = 26;

  private static final int LAYOUT_ACTIVITYDEPOSITFUNDSCOPY = 27;

  private static final int LAYOUT_ACTIVITYEDITPAYPAL = 28;

  private static final int LAYOUT_ACTIVITYEMAILVERIFY = 29;

  private static final int LAYOUT_ACTIVITYEXPERTFILTER = 30;

  private static final int LAYOUT_ACTIVITYEXPERTGIGFILTER = 31;

  private static final int LAYOUT_ACTIVITYFINDEXPERT = 32;

  private static final int LAYOUT_ACTIVITYFREELANCERPROFILE = 33;

  private static final int LAYOUT_ACTIVITYGETDISCOUNT = 34;

  private static final int LAYOUT_ACTIVITYGIGDETAILS = 35;

  private static final int LAYOUT_ACTIVITYGIGEXTRAS = 36;

  private static final int LAYOUT_ACTIVITYHIRE = 37;

  private static final int LAYOUT_ACTIVITYHOME = 38;

  private static final int LAYOUT_ACTIVITYHOWITWORKS = 39;

  private static final int LAYOUT_ACTIVITYINFSERVICE = 40;

  private static final int LAYOUT_ACTIVITYINFLPROFILEALL = 41;

  private static final int LAYOUT_ACTIVITYINFLUENCERFILTER = 42;

  private static final int LAYOUT_ACTIVITYINFLUENCERPROFILE = 43;

  private static final int LAYOUT_ACTIVITYINFLUENCERPROFILECOPY = 44;

  private static final int LAYOUT_ACTIVITYJOBPOSTTITLE = 45;

  private static final int LAYOUT_ACTIVITYLAWYERHOME = 46;

  private static final int LAYOUT_ACTIVITYLAWYERHOMENEW = 47;

  private static final int LAYOUT_ACTIVITYLOGINSIGNUP = 48;

  private static final int LAYOUT_ACTIVITYMAIN = 49;

  private static final int LAYOUT_ACTIVITYMAINTAINANCE = 50;

  private static final int LAYOUT_ACTIVITYMILESTONE = 51;

  private static final int LAYOUT_ACTIVITYMYCAMPAIGN = 52;

  private static final int LAYOUT_ACTIVITYMYINVOICES = 53;

  private static final int LAYOUT_ACTIVITYMYORDERS = 54;

  private static final int LAYOUT_ACTIVITYMYPROFILE = 55;

  private static final int LAYOUT_ACTIVITYMYPROJECTS = 56;

  private static final int LAYOUT_ACTIVITYNEWPOLICY = 57;

  private static final int LAYOUT_ACTIVITYNOTIFICATION = 58;

  private static final int LAYOUT_ACTIVITYOFFERSUMMARY = 59;

  private static final int LAYOUT_ACTIVITYOFFERTITLE = 60;

  private static final int LAYOUT_ACTIVITYORDERDETAILS = 61;

  private static final int LAYOUT_ACTIVITYOTP = 62;

  private static final int LAYOUT_ACTIVITYOTPVERIFY = 63;

  private static final int LAYOUT_ACTIVITYPARTNERABOUT = 64;

  private static final int LAYOUT_ACTIVITYPARTNERPROFILE = 65;

  private static final int LAYOUT_ACTIVITYPARTNERWITHUS = 66;

  private static final int LAYOUT_ACTIVITYPAYMENT = 67;

  private static final int LAYOUT_ACTIVITYPAYMENTNEW = 68;

  private static final int LAYOUT_ACTIVITYPHONEVERIFY = 69;

  private static final int LAYOUT_ACTIVITYPOSTJOB = 70;

  private static final int LAYOUT_ACTIVITYPOSTJOBNEW = 71;

  private static final int LAYOUT_ACTIVITYPRIVATEINFO = 72;

  private static final int LAYOUT_ACTIVITYPROFILEINFO = 73;

  private static final int LAYOUT_ACTIVITYPROFILESTARS = 74;

  private static final int LAYOUT_ACTIVITYPROJECTDETAILS = 75;

  private static final int LAYOUT_ACTIVITYPUBLICPROFILE = 76;

  private static final int LAYOUT_ACTIVITYSEARCHTAGS = 77;

  private static final int LAYOUT_ACTIVITYSELECTACCOUNT = 78;

  private static final int LAYOUT_ACTIVITYSELECTEXPERTISE = 79;

  private static final int LAYOUT_ACTIVITYSELECTFREELANCER = 80;

  private static final int LAYOUT_ACTIVITYSELLERSSERVICE = 81;

  private static final int LAYOUT_ACTIVITYSERVICE = 82;

  private static final int LAYOUT_ACTIVITYSERVICESELLERSSEARCH = 83;

  private static final int LAYOUT_ACTIVITYSERVICES = 84;

  private static final int LAYOUT_ACTIVITYSHAREDISCOUNT = 85;

  private static final int LAYOUT_ACTIVITYSPLASH = 86;

  private static final int LAYOUT_ACTIVITYUPDATELOCATION = 87;

  private static final int LAYOUT_ACTIVITYUPDATEPASSWORD = 88;

  private static final int LAYOUT_ACTIVITYUSERNAME = 89;

  private static final int LAYOUT_ACTIVITYVERIFICATION = 90;

  private static final int LAYOUT_ACTIVITYVERIFYPAYMENT = 91;

  private static final int LAYOUT_ACTIVITYVIEWALLGIG = 92;

  private static final int LAYOUT_ACTIVITYWALLET = 93;

  private static final int LAYOUT_ACTIVITYWALLETADDBALANCE = 94;

  private static final int LAYOUT_ACTIVITYWEBVIEW = 95;

  private static final int LAYOUT_ACTIVITYWHATWEDO = 96;

  private static final int LAYOUT_ACTIVITYWITHDRAWMONEY = 97;

  private static final int LAYOUT_CHATMOREOPTIONS = 98;

  private static final int LAYOUT_CHIP = 99;

  private static final int LAYOUT_CONTENTADDCARD = 100;

  private static final int LAYOUT_CONTENTCARDLIST = 101;

  private static final int LAYOUT_CUSTOMDIALOG = 102;

  private static final int LAYOUT_CUSTOMPARTNERABOUTVIEW = 103;

  private static final int LAYOUT_CUSTOMPARTNERTEXTVIEW = 104;

  private static final int LAYOUT_CUSTOMTOAST = 105;

  private static final int LAYOUT_DIALOGADDCONTACT = 106;

  private static final int LAYOUT_DIALOGBALANCEDEPOSIT = 107;

  private static final int LAYOUT_DIALOGBANKTRANSFERDONE = 108;

  private static final int LAYOUT_DIALOGCAMERADOCUMENTSELECT = 109;

  private static final int LAYOUT_DIALOGCANCELFREELANCER = 110;

  private static final int LAYOUT_DIALOGCHATNOW = 111;

  private static final int LAYOUT_DIALOGCHATSETTING = 112;

  private static final int LAYOUT_DIALOGCHOOSEPLATFORM = 113;

  private static final int LAYOUT_DIALOGCLOSEPROJECT = 114;

  private static final int LAYOUT_DIALOGCURRENCY = 115;

  private static final int LAYOUT_DIALOGCUSTOMPRICE = 116;

  private static final int LAYOUT_DIALOGCUSTOMPRICEOPTION = 117;

  private static final int LAYOUT_DIALOGDELETEACCOUNT = 118;

  private static final int LAYOUT_DIALOGDELETEPROJECT = 119;

  private static final int LAYOUT_DIALOGDEPOSIT = 120;

  private static final int LAYOUT_DIALOGDEPOSITNOTES = 121;

  private static final int LAYOUT_DIALOGEMAILVERIFICATION = 122;

  private static final int LAYOUT_DIALOGENTERCVV = 123;

  private static final int LAYOUT_DIALOGFEEDBACK = 124;

  private static final int LAYOUT_DIALOGFILEOPEN = 125;

  private static final int LAYOUT_DIALOGFILEOPTIONMENU = 126;

  private static final int LAYOUT_DIALOGFORGOTPASSWORD = 127;

  private static final int LAYOUT_DIALOGFREETRIAL = 128;

  private static final int LAYOUT_DIALOGITEMSELECTBLACK = 129;

  private static final int LAYOUT_DIALOGLANGUAGE = 130;

  private static final int LAYOUT_DIALOGLOCATIONDISCLOSURE = 131;

  private static final int LAYOUT_DIALOGLOCATIONSKILL = 132;

  private static final int LAYOUT_DIALOGLOGINNEW = 133;

  private static final int LAYOUT_DIALOGLOGINSIGNUP = 134;

  private static final int LAYOUT_DIALOGLOGOUT = 135;

  private static final int LAYOUT_DIALOGMYPROFILEDETAIL = 136;

  private static final int LAYOUT_DIALOGNOINTERNET = 137;

  private static final int LAYOUT_DIALOGOPENWEBSITE = 138;

  private static final int LAYOUT_DIALOGPAYDONE = 139;

  private static final int LAYOUT_DIALOGPAYMENTDEPOSIT = 140;

  private static final int LAYOUT_DIALOGPAYMENTDEPOSITDONE = 141;

  private static final int LAYOUT_DIALOGPAYMENTDONE = 142;

  private static final int LAYOUT_DIALOGPOSTINGDONE = 143;

  private static final int LAYOUT_DIALOGPROMOCODE = 144;

  private static final int LAYOUT_DIALOGRATEAPP = 145;

  private static final int LAYOUT_DIALOGRATEME = 146;

  private static final int LAYOUT_DIALOGREFUNDPAYMENT = 147;

  private static final int LAYOUT_DIALOGREFUNDREASON = 148;

  private static final int LAYOUT_DIALOGREFUNDUSER = 149;

  private static final int LAYOUT_DIALOGRELEASEPAYMENT = 150;

  private static final int LAYOUT_DIALOGREPOSTDELETEJOB = 151;

  private static final int LAYOUT_DIALOGSECURITYCODE = 152;

  private static final int LAYOUT_DIALOGSORTBYFILTER = 153;

  private static final int LAYOUT_DIALOGSTARS = 154;

  private static final int LAYOUT_DIALOGSTORAGEDISCLOSURE = 155;

  private static final int LAYOUT_DIALOGTIMELINE = 156;

  private static final int LAYOUT_DIALOGWITHDRAW = 157;

  private static final int LAYOUT_DIALOGZOOMPORTFOLIOIMAGE = 158;

  private static final int LAYOUT_FRAGMENTABOUTPROFILE = 159;

  private static final int LAYOUT_FRAGMENTALLPOPULARLAWYER = 160;

  private static final int LAYOUT_FRAGMENTATTACHMENT = 161;

  private static final int LAYOUT_FRAGMENTBALANCEDEPOSIT = 162;

  private static final int LAYOUT_FRAGMENTBALANCEHISTORY = 163;

  private static final int LAYOUT_FRAGMENTBALANCEPAYMENT = 164;

  private static final int LAYOUT_FRAGMENTCAMPDETAIL = 165;

  private static final int LAYOUT_FRAGMENTCAMPPAY = 166;

  private static final int LAYOUT_FRAGMENTCAMPSTARS = 167;

  private static final int LAYOUT_FRAGMENTCHATLIST = 168;

  private static final int LAYOUT_FRAGMENTCHATMANAGER = 169;

  private static final int LAYOUT_FRAGMENTCHOOSEDEVELOPER = 170;

  private static final int LAYOUT_FRAGMENTCHOOSESKILLS = 171;

  private static final int LAYOUT_FRAGMENTDEADLINE = 172;

  private static final int LAYOUT_FRAGMENTDEPOSITFUNDS = 173;

  private static final int LAYOUT_FRAGMENTDESCRIBE = 174;

  private static final int LAYOUT_FRAGMENTEARNMONEY = 175;

  private static final int LAYOUT_FRAGMENTENTERRATE = 176;

  private static final int LAYOUT_FRAGMENTINFAGENCY = 177;

  private static final int LAYOUT_FRAGMENTINFALL = 178;

  private static final int LAYOUT_FRAGMENTINFSERVICES = 179;

  private static final int LAYOUT_FRAGMENTLAWYERSERVICE = 180;

  private static final int LAYOUT_FRAGMENTLIVECHAT = 181;

  private static final int LAYOUT_FRAGMENTMYLEVEL = 182;

  private static final int LAYOUT_FRAGMENTPAYTYPE = 183;

  private static final int LAYOUT_FRAGMENTPOSTJOB = 184;

  private static final int LAYOUT_FRAGMENTPROJECTDETAILS = 185;

  private static final int LAYOUT_FRAGMENTPROJECTPAYMENTNEW = 186;

  private static final int LAYOUT_FRAGMENTPROJECTRATE = 187;

  private static final int LAYOUT_FRAGMENTPROJECTSTATUS = 188;

  private static final int LAYOUT_FRAGMENTPROJECTSUBMIT = 189;

  private static final int LAYOUT_FRAGMENTPROJECTSLIST = 190;

  private static final int LAYOUT_FRAGMENTREVIEWSPROFILE = 191;

  private static final int LAYOUT_FRAGMENTSELECTRATE = 192;

  private static final int LAYOUT_FRAGMENTSELECTSERVICE = 193;

  private static final int LAYOUT_FRAGMENTSKILLPROFILE = 194;

  private static final int LAYOUT_FRAGMENTWANTTOPAY = 195;

  private static final int LAYOUT_FRAGMENTWIN = 196;

  private static final int LAYOUT_HIREGRIDITEM = 197;

  private static final int LAYOUT_HOMEGRIDITEM = 198;

  private static final int LAYOUT_HOMELISTITEM = 199;

  private static final int LAYOUT_HOMEPAGEITEM = 200;

  private static final int LAYOUT_ITEMACCOUNT = 201;

  private static final int LAYOUT_ITEMBANNERLAWYER = 202;

  private static final int LAYOUT_ITEMCAMPAIGNINPROGRESS = 203;

  private static final int LAYOUT_ITEMCAMPAIGNSTARS = 204;

  private static final int LAYOUT_ITEMCARDLIST = 205;

  private static final int LAYOUT_ITEMCARDVIEW = 206;

  private static final int LAYOUT_ITEMCATEGORYLIST = 207;

  private static final int LAYOUT_ITEMCHATLIST = 208;

  private static final int LAYOUT_ITEMCHATMESSAGES = 209;

  private static final int LAYOUT_ITEMCHATMSG = 210;

  private static final int LAYOUT_ITEMCHIPVIEW = 211;

  private static final int LAYOUT_ITEMCHOOSEACCOUNT = 212;

  private static final int LAYOUT_ITEMCLIENTREVIEW = 213;

  private static final int LAYOUT_ITEMCUSTOMGIGDETAILS = 214;

  private static final int LAYOUT_ITEMCUSTOMGIGPROJECTDETAILS = 215;

  private static final int LAYOUT_ITEMCUSTOMPRICE = 216;

  private static final int LAYOUT_ITEMEXPERTLIST = 217;

  private static final int LAYOUT_ITEMEXPERTLISTPLACEHOLDER = 218;

  private static final int LAYOUT_ITEMFILESDESC = 219;

  private static final int LAYOUT_ITEMFILTERSERVICE = 220;

  private static final int LAYOUT_ITEMFOLLWERSLIST = 221;

  private static final int LAYOUT_ITEMGIGDETAILPAGER = 222;

  private static final int LAYOUT_ITEMGIGDETAILS = 223;

  private static final int LAYOUT_ITEMGIGDETAILSPLACEHOLDER = 224;

  private static final int LAYOUT_ITEMGIGEXTRAS = 225;

  private static final int LAYOUT_ITEMGIGHOME = 226;

  private static final int LAYOUT_ITEMGIGHOMENEW = 227;

  private static final int LAYOUT_ITEMGIGPLACEHOLDER = 228;

  private static final int LAYOUT_ITEMHIRE = 229;

  private static final int LAYOUT_ITEMHOME = 230;

  private static final int LAYOUT_ITEMHOMECATEGORY = 231;

  private static final int LAYOUT_ITEMHOMECATEGORYLAWYER = 232;

  private static final int LAYOUT_ITEMHOMEFOLLOWER = 233;

  private static final int LAYOUT_ITEMHOMEISTOP = 234;

  private static final int LAYOUT_ITEMINCOMEBALANE = 235;

  private static final int LAYOUT_ITEMINFREVIEWS = 236;

  private static final int LAYOUT_ITEMINFSERVICE = 237;

  private static final int LAYOUT_ITEMINFSERVICENEW = 238;

  private static final int LAYOUT_ITEMINFSERVICES = 239;

  private static final int LAYOUT_ITEMINFSTORE = 240;

  private static final int LAYOUT_ITEMJOBTITLE = 241;

  private static final int LAYOUT_ITEMLANGUAGEAGENTS = 242;

  private static final int LAYOUT_ITEMLISTFILES = 243;

  private static final int LAYOUT_ITEMLISTFILESSURVEY = 244;

  private static final int LAYOUT_ITEMMYINVOICE = 245;

  private static final int LAYOUT_ITEMMYSTORE = 246;

  private static final int LAYOUT_ITEMNOTIFICATION = 247;

  private static final int LAYOUT_ITEMNOTIFICATIONPH = 248;

  private static final int LAYOUT_ITEMORDERSTARS = 249;

  private static final int LAYOUT_ITEMORDERS = 250;

  private static final int LAYOUT_ITEMORDERSPH = 251;

  private static final int LAYOUT_ITEMORDERSTEXT = 252;

  private static final int LAYOUT_ITEMPARTNERANSWER = 253;

  private static final int LAYOUT_ITEMPARTNERS = 254;

  private static final int LAYOUT_ITEMPLATFORM = 255;

  private static final int LAYOUT_ITEMPLATFORMIMG = 256;

  private static final int LAYOUT_ITEMPLATFORMSELECTION = 257;

  private static final int LAYOUT_ITEMPOLICY = 258;

  private static final int LAYOUT_ITEMPOPULAR = 259;

  private static final int LAYOUT_ITEMPOPULARPLACEHOLDER = 260;

  private static final int LAYOUT_ITEMPORTFOLIOLIST = 261;

  private static final int LAYOUT_ITEMPORTFOLIOLISTPH = 262;

  private static final int LAYOUT_ITEMPORTFOLIOSMALL = 263;

  private static final int LAYOUT_ITEMPROFILEPRODUCT = 264;

  private static final int LAYOUT_ITEMPROJECTSLIST = 265;

  private static final int LAYOUT_ITEMPROPOSALFREELANCERLIST = 266;

  private static final int LAYOUT_ITEMPROPOSALLIST = 267;

  private static final int LAYOUT_ITEMREFERRALHISTORY = 268;

  private static final int LAYOUT_ITEMREVIEWS = 269;

  private static final int LAYOUT_ITEMREVIEWSPH = 270;

  private static final int LAYOUT_ITEMSELECTFULL = 271;

  private static final int LAYOUT_ITEMSELECTLANGUAGE = 272;

  private static final int LAYOUT_ITEMSELECTRATE = 273;

  private static final int LAYOUT_ITEMSELECTSERVICE = 274;

  private static final int LAYOUT_ITEMSELECTEDSTARS = 275;

  private static final int LAYOUT_ITEMSERVICE = 276;

  private static final int LAYOUT_ITEMSERVICEPLATFORM = 277;

  private static final int LAYOUT_ITEMSERVICESELLERS = 278;

  private static final int LAYOUT_ITEMSINGLEMULTISELECTION = 279;

  private static final int LAYOUT_ITEMSKILLFILTER = 280;

  private static final int LAYOUT_ITEMSKILLS = 281;

  private static final int LAYOUT_ITEMSKILLSEDIT = 282;

  private static final int LAYOUT_ITEMSKILLSPOST = 283;

  private static final int LAYOUT_ITEMSM = 284;

  private static final int LAYOUT_ITEMSOCIAL = 285;

  private static final int LAYOUT_ITEMSOCIALGIG = 286;

  private static final int LAYOUT_ITEMSOCIALGIGDETAILS = 287;

  private static final int LAYOUT_ITEMSOCIALINFSERV = 288;

  private static final int LAYOUT_ITEMSOCIALMEDIA = 289;

  private static final int LAYOUT_ITEMSOCIALMEDIAPROFILE = 290;

  private static final int LAYOUT_ITEMSOCIALSERVICE = 291;

  private static final int LAYOUT_ITEMSTARS = 292;

  private static final int LAYOUT_ITEMSTARSPLATFORM = 293;

  private static final int LAYOUT_ITEMSUBMITTEDFILES = 294;

  private static final int LAYOUT_ITEMTAGVIEW = 295;

  private static final int LAYOUT_ITEMUPLOADEDFILES = 296;

  private static final int LAYOUT_ITEMUSERREHIRE = 297;

  private static final int LAYOUT_ITEMVERIFICATION = 298;

  private static final int LAYOUT_ITEMVERIFIEDWITH = 299;

  private static final int LAYOUT_ITEMVIEWALLCATEGORY = 300;

  private static final int LAYOUT_ITEMWALLET = 301;

  private static final int LAYOUT_ITEMWORKWITH = 302;

  private static final int LAYOUT_ITEMYOUTUBEPROFILE = 303;

  private static final int LAYOUT_LAYIMAGE = 304;

  private static final int LAYOUT_LAYTEXT = 305;

  private static final int LAYOUT_LAYOUTHOMEHOWWORK = 306;

  private static final int LAYOUT_LAYOUTMILESTONEPAYMENT = 307;

  private static final int LAYOUT_LAYOUTTEXTVIEW = 308;

  private static final int LAYOUT_NODATALAYOUT = 309;

  private static final int LAYOUT_TIMELINEITEM = 310;

  private static final int LAYOUT_TOOLBARBACK = 311;

  private static final int LAYOUT_TOOLBARO = 312;

  private static final int LAYOUT_TOOLBARPROGRESSNEXT = 313;

  private static final int LAYOUT_TOOLBARSAVE = 314;

  private static final int LAYOUT_TOOLBARTITLE = 315;

  private static final int LAYOUT_VIEWAGENCY = 316;

  private static final int LAYOUT_VIEWMYSTORE = 317;

  private static final int LAYOUT_VIEWOVERVIEW = 318;

  private static final int LAYOUT_VIEWPARTNER = 319;

  private static final int LAYOUT_VIEWPORTFOLIO = 320;

  private static final int LAYOUT_VIEWSERVICES = 321;

  private static final int LAYOUT_VIEWSOCIALMEDIA = 322;

  private static final int LAYOUT_VIEWWORKWITH = 323;

  private static final int LAYOUT_VIEWYOUTUBE = 324;

  private static final int LAYOUT_VWLAYOUTITEMAUDIOPICK = 325;

  private static final int LAYOUT_VWLAYOUTITEMFOLDERLIST = 326;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(326);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_account_delete, LAYOUT_ACTIVITYACCOUNTDELETE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_add_card, LAYOUT_ACTIVITYADDCARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_add_describe, LAYOUT_ACTIVITYADDDESCRIBE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_add_money, LAYOUT_ACTIVITYADDMONEY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_add_paypal, LAYOUT_ACTIVITYADDPAYPAL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_add_star, LAYOUT_ACTIVITYADDSTAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_add_survey, LAYOUT_ACTIVITYADDSURVEY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_add_survey_submit, LAYOUT_ACTIVITYADDSURVEYSUBMIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_auto_palce_location, LAYOUT_ACTIVITYAUTOPALCELOCATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_balance, LAYOUT_ACTIVITYBALANCE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_bank_transfer, LAYOUT_ACTIVITYBANKTRANSFER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_caller, LAYOUT_ACTIVITYCALLER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_camp_data, LAYOUT_ACTIVITYCAMPDATA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_camp_details, LAYOUT_ACTIVITYCAMPDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_camp_stars, LAYOUT_ACTIVITYCAMPSTARS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_campaign_details, LAYOUT_ACTIVITYCAMPAIGNDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_chat, LAYOUT_ACTIVITYCHAT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_chat_messages, LAYOUT_ACTIVITYCHATMESSAGES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_choose_account, LAYOUT_ACTIVITYCHOOSEACCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_choose_payment_method, LAYOUT_ACTIVITYCHOOSEPAYMENTMETHOD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_client_more, LAYOUT_ACTIVITYCLIENTMORE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_client_profile, LAYOUT_ACTIVITYCLIENTPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_client_review, LAYOUT_ACTIVITYCLIENTREVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_client_setting, LAYOUT_ACTIVITYCLIENTSETTING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_client_settings, LAYOUT_ACTIVITYCLIENTSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_deposit, LAYOUT_ACTIVITYDEPOSIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_deposit_funds_copy, LAYOUT_ACTIVITYDEPOSITFUNDSCOPY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_edit_paypal, LAYOUT_ACTIVITYEDITPAYPAL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_email_verify, LAYOUT_ACTIVITYEMAILVERIFY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_expert_filter, LAYOUT_ACTIVITYEXPERTFILTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_expert_gig_filter, LAYOUT_ACTIVITYEXPERTGIGFILTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_find_expert, LAYOUT_ACTIVITYFINDEXPERT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_freelancer_profile, LAYOUT_ACTIVITYFREELANCERPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_get_discount, LAYOUT_ACTIVITYGETDISCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_gig_details, LAYOUT_ACTIVITYGIGDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_gig_extras, LAYOUT_ACTIVITYGIGEXTRAS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_hire, LAYOUT_ACTIVITYHIRE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_home, LAYOUT_ACTIVITYHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_how_it_works, LAYOUT_ACTIVITYHOWITWORKS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_inf_service, LAYOUT_ACTIVITYINFSERVICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_infl_profile_all, LAYOUT_ACTIVITYINFLPROFILEALL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_influencer_filter, LAYOUT_ACTIVITYINFLUENCERFILTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_influencer_profile, LAYOUT_ACTIVITYINFLUENCERPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_influencer_profile_copy, LAYOUT_ACTIVITYINFLUENCERPROFILECOPY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_job_post_title, LAYOUT_ACTIVITYJOBPOSTTITLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_lawyer_home, LAYOUT_ACTIVITYLAWYERHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_lawyer_home_new, LAYOUT_ACTIVITYLAWYERHOMENEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_login_sign_up, LAYOUT_ACTIVITYLOGINSIGNUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_maintainance, LAYOUT_ACTIVITYMAINTAINANCE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_milestone, LAYOUT_ACTIVITYMILESTONE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_my_campaign, LAYOUT_ACTIVITYMYCAMPAIGN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_my_invoices, LAYOUT_ACTIVITYMYINVOICES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_my_orders, LAYOUT_ACTIVITYMYORDERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_my_profile, LAYOUT_ACTIVITYMYPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_my_projects, LAYOUT_ACTIVITYMYPROJECTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_new_policy, LAYOUT_ACTIVITYNEWPOLICY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_notification, LAYOUT_ACTIVITYNOTIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_offer_summary, LAYOUT_ACTIVITYOFFERSUMMARY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_offer_title, LAYOUT_ACTIVITYOFFERTITLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_order_details, LAYOUT_ACTIVITYORDERDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_otp, LAYOUT_ACTIVITYOTP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_otp_verify, LAYOUT_ACTIVITYOTPVERIFY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_partner_about, LAYOUT_ACTIVITYPARTNERABOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_partner_profile, LAYOUT_ACTIVITYPARTNERPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_partner_with_us, LAYOUT_ACTIVITYPARTNERWITHUS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_payment, LAYOUT_ACTIVITYPAYMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_payment_new, LAYOUT_ACTIVITYPAYMENTNEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_phone_verify, LAYOUT_ACTIVITYPHONEVERIFY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_post_job, LAYOUT_ACTIVITYPOSTJOB);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_post_job_new, LAYOUT_ACTIVITYPOSTJOBNEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_private_info, LAYOUT_ACTIVITYPRIVATEINFO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_profile_info, LAYOUT_ACTIVITYPROFILEINFO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_profile_stars, LAYOUT_ACTIVITYPROFILESTARS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_project_details, LAYOUT_ACTIVITYPROJECTDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_public_profile, LAYOUT_ACTIVITYPUBLICPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_search_tags, LAYOUT_ACTIVITYSEARCHTAGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_select_account, LAYOUT_ACTIVITYSELECTACCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_select_expertise, LAYOUT_ACTIVITYSELECTEXPERTISE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_select_freelancer, LAYOUT_ACTIVITYSELECTFREELANCER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_sellers_service, LAYOUT_ACTIVITYSELLERSSERVICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_service, LAYOUT_ACTIVITYSERVICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_service_sellers_search, LAYOUT_ACTIVITYSERVICESELLERSSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_services, LAYOUT_ACTIVITYSERVICES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_share_discount, LAYOUT_ACTIVITYSHAREDISCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_splash, LAYOUT_ACTIVITYSPLASH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_update_location, LAYOUT_ACTIVITYUPDATELOCATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_update_password, LAYOUT_ACTIVITYUPDATEPASSWORD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_username, LAYOUT_ACTIVITYUSERNAME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_verification, LAYOUT_ACTIVITYVERIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_verify_payment, LAYOUT_ACTIVITYVERIFYPAYMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_view_all_gig, LAYOUT_ACTIVITYVIEWALLGIG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_wallet, LAYOUT_ACTIVITYWALLET);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_wallet_add_balance, LAYOUT_ACTIVITYWALLETADDBALANCE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_web_view, LAYOUT_ACTIVITYWEBVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_what_we_do, LAYOUT_ACTIVITYWHATWEDO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.activity_withdraw_money, LAYOUT_ACTIVITYWITHDRAWMONEY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.chat_more_options, LAYOUT_CHATMOREOPTIONS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.chip, LAYOUT_CHIP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.content_addcard, LAYOUT_CONTENTADDCARD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.content_card_list, LAYOUT_CONTENTCARDLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.custom_dialog, LAYOUT_CUSTOMDIALOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.custom_partner_about_view, LAYOUT_CUSTOMPARTNERABOUTVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.custom_partner_textview, LAYOUT_CUSTOMPARTNERTEXTVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.custom_toast, LAYOUT_CUSTOMTOAST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_add_contact, LAYOUT_DIALOGADDCONTACT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_balance_deposit, LAYOUT_DIALOGBALANCEDEPOSIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_bank_transfer_done, LAYOUT_DIALOGBANKTRANSFERDONE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_camera_document_select, LAYOUT_DIALOGCAMERADOCUMENTSELECT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_cancel_freelancer, LAYOUT_DIALOGCANCELFREELANCER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_chat_now, LAYOUT_DIALOGCHATNOW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_chat_setting, LAYOUT_DIALOGCHATSETTING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_choose_platform, LAYOUT_DIALOGCHOOSEPLATFORM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_close_project, LAYOUT_DIALOGCLOSEPROJECT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_currency, LAYOUT_DIALOGCURRENCY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_custom_price, LAYOUT_DIALOGCUSTOMPRICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_custom_price_option, LAYOUT_DIALOGCUSTOMPRICEOPTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_delete_account, LAYOUT_DIALOGDELETEACCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_delete_project, LAYOUT_DIALOGDELETEPROJECT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_deposit, LAYOUT_DIALOGDEPOSIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_deposit_notes, LAYOUT_DIALOGDEPOSITNOTES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_email_verification, LAYOUT_DIALOGEMAILVERIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_enter_cvv, LAYOUT_DIALOGENTERCVV);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_feedback, LAYOUT_DIALOGFEEDBACK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_file_open, LAYOUT_DIALOGFILEOPEN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_file_option_menu, LAYOUT_DIALOGFILEOPTIONMENU);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_forgot_password, LAYOUT_DIALOGFORGOTPASSWORD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_free_trial, LAYOUT_DIALOGFREETRIAL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_item_select_black, LAYOUT_DIALOGITEMSELECTBLACK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_language, LAYOUT_DIALOGLANGUAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_location_disclosure, LAYOUT_DIALOGLOCATIONDISCLOSURE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_location_skill, LAYOUT_DIALOGLOCATIONSKILL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_login_new, LAYOUT_DIALOGLOGINNEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_login_sign_up, LAYOUT_DIALOGLOGINSIGNUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_logout, LAYOUT_DIALOGLOGOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_my_profile_detail, LAYOUT_DIALOGMYPROFILEDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_no_internet, LAYOUT_DIALOGNOINTERNET);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_open_website, LAYOUT_DIALOGOPENWEBSITE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_pay_done, LAYOUT_DIALOGPAYDONE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_payment_deposit, LAYOUT_DIALOGPAYMENTDEPOSIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_payment_deposit_done, LAYOUT_DIALOGPAYMENTDEPOSITDONE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_payment_done, LAYOUT_DIALOGPAYMENTDONE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_posting_done, LAYOUT_DIALOGPOSTINGDONE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_promo_code, LAYOUT_DIALOGPROMOCODE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_rate_app, LAYOUT_DIALOGRATEAPP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_rate_me, LAYOUT_DIALOGRATEME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_refund_payment, LAYOUT_DIALOGREFUNDPAYMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_refund_reason, LAYOUT_DIALOGREFUNDREASON);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_refund_user, LAYOUT_DIALOGREFUNDUSER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_release_payment, LAYOUT_DIALOGRELEASEPAYMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_repost_delete_job, LAYOUT_DIALOGREPOSTDELETEJOB);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_security_code, LAYOUT_DIALOGSECURITYCODE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_sort_by_filter, LAYOUT_DIALOGSORTBYFILTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_stars, LAYOUT_DIALOGSTARS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_storage_disclosure, LAYOUT_DIALOGSTORAGEDISCLOSURE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_timeline, LAYOUT_DIALOGTIMELINE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_withdraw, LAYOUT_DIALOGWITHDRAW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.dialog_zoom_portfolio_image, LAYOUT_DIALOGZOOMPORTFOLIOIMAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_about_profile, LAYOUT_FRAGMENTABOUTPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_all_popular_lawyer, LAYOUT_FRAGMENTALLPOPULARLAWYER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_attachment, LAYOUT_FRAGMENTATTACHMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_balance_deposit, LAYOUT_FRAGMENTBALANCEDEPOSIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_balance_history, LAYOUT_FRAGMENTBALANCEHISTORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_balance_payment, LAYOUT_FRAGMENTBALANCEPAYMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_camp_detail, LAYOUT_FRAGMENTCAMPDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_camp_pay, LAYOUT_FRAGMENTCAMPPAY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_camp_stars, LAYOUT_FRAGMENTCAMPSTARS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_chat_list, LAYOUT_FRAGMENTCHATLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_chat_manager, LAYOUT_FRAGMENTCHATMANAGER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_choose_developer, LAYOUT_FRAGMENTCHOOSEDEVELOPER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_choose_skills, LAYOUT_FRAGMENTCHOOSESKILLS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_deadline, LAYOUT_FRAGMENTDEADLINE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_deposit_funds, LAYOUT_FRAGMENTDEPOSITFUNDS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_describe, LAYOUT_FRAGMENTDESCRIBE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_earn_money, LAYOUT_FRAGMENTEARNMONEY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_enter_rate, LAYOUT_FRAGMENTENTERRATE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_inf_agency, LAYOUT_FRAGMENTINFAGENCY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_inf_all, LAYOUT_FRAGMENTINFALL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_inf_services, LAYOUT_FRAGMENTINFSERVICES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_lawyer_service, LAYOUT_FRAGMENTLAWYERSERVICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_live_chat, LAYOUT_FRAGMENTLIVECHAT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_my_level, LAYOUT_FRAGMENTMYLEVEL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_pay_type, LAYOUT_FRAGMENTPAYTYPE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_post_job, LAYOUT_FRAGMENTPOSTJOB);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_project_details, LAYOUT_FRAGMENTPROJECTDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_project_payment_new, LAYOUT_FRAGMENTPROJECTPAYMENTNEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_project_rate, LAYOUT_FRAGMENTPROJECTRATE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_project_status, LAYOUT_FRAGMENTPROJECTSTATUS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_project_submit, LAYOUT_FRAGMENTPROJECTSUBMIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_projects_list, LAYOUT_FRAGMENTPROJECTSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_reviews_profile, LAYOUT_FRAGMENTREVIEWSPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_select_rate, LAYOUT_FRAGMENTSELECTRATE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_select_service, LAYOUT_FRAGMENTSELECTSERVICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_skill_profile, LAYOUT_FRAGMENTSKILLPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_want_to_pay, LAYOUT_FRAGMENTWANTTOPAY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.fragment_win, LAYOUT_FRAGMENTWIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.hire_grid_item, LAYOUT_HIREGRIDITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.home_grid_item, LAYOUT_HOMEGRIDITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.home_list_item, LAYOUT_HOMELISTITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.home_page_item, LAYOUT_HOMEPAGEITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_account, LAYOUT_ITEMACCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_banner_lawyer, LAYOUT_ITEMBANNERLAWYER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_campaign_inprogress, LAYOUT_ITEMCAMPAIGNINPROGRESS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_campaign_stars, LAYOUT_ITEMCAMPAIGNSTARS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_card_list, LAYOUT_ITEMCARDLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_card_view, LAYOUT_ITEMCARDVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_category_list, LAYOUT_ITEMCATEGORYLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_chat_list, LAYOUT_ITEMCHATLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_chat_messages, LAYOUT_ITEMCHATMESSAGES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_chat_msg, LAYOUT_ITEMCHATMSG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_chip_view, LAYOUT_ITEMCHIPVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_choose_account, LAYOUT_ITEMCHOOSEACCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_client_review, LAYOUT_ITEMCLIENTREVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_custom_gig_details, LAYOUT_ITEMCUSTOMGIGDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_custom_gig_project_details, LAYOUT_ITEMCUSTOMGIGPROJECTDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_custom_price, LAYOUT_ITEMCUSTOMPRICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_expert_list, LAYOUT_ITEMEXPERTLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_expert_list_placeholder, LAYOUT_ITEMEXPERTLISTPLACEHOLDER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_files_desc, LAYOUT_ITEMFILESDESC);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_filter_service, LAYOUT_ITEMFILTERSERVICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_follwers_list, LAYOUT_ITEMFOLLWERSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_gig_detail_pager, LAYOUT_ITEMGIGDETAILPAGER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_gig_details, LAYOUT_ITEMGIGDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_gig_details_placeholder, LAYOUT_ITEMGIGDETAILSPLACEHOLDER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_gig_extras, LAYOUT_ITEMGIGEXTRAS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_gig_home, LAYOUT_ITEMGIGHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_gig_home_new, LAYOUT_ITEMGIGHOMENEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_gig_placeholder, LAYOUT_ITEMGIGPLACEHOLDER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_hire, LAYOUT_ITEMHIRE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_home, LAYOUT_ITEMHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_home_category, LAYOUT_ITEMHOMECATEGORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_home_category_lawyer, LAYOUT_ITEMHOMECATEGORYLAWYER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_home_follower, LAYOUT_ITEMHOMEFOLLOWER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_home_is_top, LAYOUT_ITEMHOMEISTOP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_income_balane, LAYOUT_ITEMINCOMEBALANE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_inf_reviews, LAYOUT_ITEMINFREVIEWS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_inf_service, LAYOUT_ITEMINFSERVICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_inf_service_new, LAYOUT_ITEMINFSERVICENEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_inf_services, LAYOUT_ITEMINFSERVICES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_inf_store, LAYOUT_ITEMINFSTORE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_job_title, LAYOUT_ITEMJOBTITLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_language_agents, LAYOUT_ITEMLANGUAGEAGENTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_list_files, LAYOUT_ITEMLISTFILES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_list_files_survey, LAYOUT_ITEMLISTFILESSURVEY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_my_invoice, LAYOUT_ITEMMYINVOICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_my_store, LAYOUT_ITEMMYSTORE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_notification, LAYOUT_ITEMNOTIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_notification_ph, LAYOUT_ITEMNOTIFICATIONPH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_order_stars, LAYOUT_ITEMORDERSTARS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_orders, LAYOUT_ITEMORDERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_orders_ph, LAYOUT_ITEMORDERSPH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_orders_text, LAYOUT_ITEMORDERSTEXT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_partner_answer, LAYOUT_ITEMPARTNERANSWER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_partners, LAYOUT_ITEMPARTNERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_platform, LAYOUT_ITEMPLATFORM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_platform_img, LAYOUT_ITEMPLATFORMIMG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_platform_selection, LAYOUT_ITEMPLATFORMSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_policy, LAYOUT_ITEMPOLICY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_popular, LAYOUT_ITEMPOPULAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_popular_placeholder, LAYOUT_ITEMPOPULARPLACEHOLDER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_portfolio_list, LAYOUT_ITEMPORTFOLIOLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_portfolio_list_ph, LAYOUT_ITEMPORTFOLIOLISTPH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_portfolio_small, LAYOUT_ITEMPORTFOLIOSMALL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_profile_product, LAYOUT_ITEMPROFILEPRODUCT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_projects_list, LAYOUT_ITEMPROJECTSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_proposal_freelancer_list, LAYOUT_ITEMPROPOSALFREELANCERLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_proposal_list, LAYOUT_ITEMPROPOSALLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_referral_history, LAYOUT_ITEMREFERRALHISTORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_reviews, LAYOUT_ITEMREVIEWS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_reviews_ph, LAYOUT_ITEMREVIEWSPH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_select_full, LAYOUT_ITEMSELECTFULL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_select_language, LAYOUT_ITEMSELECTLANGUAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_select_rate, LAYOUT_ITEMSELECTRATE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_select_service, LAYOUT_ITEMSELECTSERVICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_selected_stars, LAYOUT_ITEMSELECTEDSTARS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_service, LAYOUT_ITEMSERVICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_service_platform, LAYOUT_ITEMSERVICEPLATFORM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_service_sellers, LAYOUT_ITEMSERVICESELLERS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_singlemultiselection, LAYOUT_ITEMSINGLEMULTISELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_skill_filter, LAYOUT_ITEMSKILLFILTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_skills, LAYOUT_ITEMSKILLS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_skills_edit, LAYOUT_ITEMSKILLSEDIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_skills_post, LAYOUT_ITEMSKILLSPOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_sm, LAYOUT_ITEMSM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_social, LAYOUT_ITEMSOCIAL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_social_gig, LAYOUT_ITEMSOCIALGIG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_social_gig_details, LAYOUT_ITEMSOCIALGIGDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_social_inf_serv, LAYOUT_ITEMSOCIALINFSERV);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_social_media, LAYOUT_ITEMSOCIALMEDIA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_social_media_profile, LAYOUT_ITEMSOCIALMEDIAPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_social_service, LAYOUT_ITEMSOCIALSERVICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_stars, LAYOUT_ITEMSTARS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_stars_platform, LAYOUT_ITEMSTARSPLATFORM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_submitted_files, LAYOUT_ITEMSUBMITTEDFILES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_tag_view, LAYOUT_ITEMTAGVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_uploaded_files, LAYOUT_ITEMUPLOADEDFILES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_user_rehire, LAYOUT_ITEMUSERREHIRE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_verification, LAYOUT_ITEMVERIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_verified_with, LAYOUT_ITEMVERIFIEDWITH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_view_all_category, LAYOUT_ITEMVIEWALLCATEGORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_wallet, LAYOUT_ITEMWALLET);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_workwith, LAYOUT_ITEMWORKWITH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.item_youtube_profile, LAYOUT_ITEMYOUTUBEPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.lay_image, LAYOUT_LAYIMAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.lay_text, LAYOUT_LAYTEXT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.layout_home_how_work, LAYOUT_LAYOUTHOMEHOWWORK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.layout_milestone_payment, LAYOUT_LAYOUTMILESTONEPAYMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.layout_textview, LAYOUT_LAYOUTTEXTVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.no_data_layout, LAYOUT_NODATALAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.timeline_item, LAYOUT_TIMELINEITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.toolbar_back, LAYOUT_TOOLBARBACK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.toolbar_o, LAYOUT_TOOLBARO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.toolbar_progress_next, LAYOUT_TOOLBARPROGRESSNEXT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.toolbar_save, LAYOUT_TOOLBARSAVE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.toolbar_title, LAYOUT_TOOLBARTITLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.view_agency, LAYOUT_VIEWAGENCY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.view_my_store, LAYOUT_VIEWMYSTORE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.view_overview, LAYOUT_VIEWOVERVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.view_partner, LAYOUT_VIEWPARTNER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.view_portfolio, LAYOUT_VIEWPORTFOLIO);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.view_services, LAYOUT_VIEWSERVICES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.view_social_media, LAYOUT_VIEWSOCIALMEDIA);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.view_workwith, LAYOUT_VIEWWORKWITH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.view_youtube, LAYOUT_VIEWYOUTUBE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.vw_layout_item_audio_pick, LAYOUT_VWLAYOUTITEMAUDIOPICK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.nojom.client.R.layout.vw_layout_item_folder_list, LAYOUT_VWLAYOUTITEMFOLDERLIST);
  }

  private final ViewDataBinding internalGetViewDataBinding0(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_ACTIVITYACCOUNTDELETE: {
        if ("layout/activity_account_delete_0".equals(tag)) {
          return new ActivityAccountDeleteBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_account_delete is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYADDCARD: {
        if ("layout/activity_add_card_0".equals(tag)) {
          return new ActivityAddCardBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_add_card is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYADDDESCRIBE: {
        if ("layout/activity_add_describe_0".equals(tag)) {
          return new ActivityAddDescribeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_add_describe is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYADDMONEY: {
        if ("layout/activity_add_money_0".equals(tag)) {
          return new ActivityAddMoneyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_add_money is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYADDPAYPAL: {
        if ("layout/activity_add_paypal_0".equals(tag)) {
          return new ActivityAddPaypalBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_add_paypal is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYADDSTAR: {
        if ("layout/activity_add_star_0".equals(tag)) {
          return new ActivityAddStarBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_add_star is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYADDSURVEY: {
        if ("layout/activity_add_survey_0".equals(tag)) {
          return new ActivityAddSurveyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_add_survey is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYADDSURVEYSUBMIT: {
        if ("layout/activity_add_survey_submit_0".equals(tag)) {
          return new ActivityAddSurveySubmitBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_add_survey_submit is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYAUTOPALCELOCATION: {
        if ("layout/activity_auto_palce_location_0".equals(tag)) {
          return new ActivityAutoPalceLocationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_auto_palce_location is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYBALANCE: {
        if ("layout/activity_balance_0".equals(tag)) {
          return new ActivityBalanceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_balance is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYBANKTRANSFER: {
        if ("layout/activity_bank_transfer_0".equals(tag)) {
          return new ActivityBankTransferBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_bank_transfer is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCALLER: {
        if ("layout/activity_caller_0".equals(tag)) {
          return new ActivityCallerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_caller is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCAMPDATA: {
        if ("layout/activity_camp_data_0".equals(tag)) {
          return new ActivityCampDataBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_camp_data is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCAMPDETAILS: {
        if ("layout/activity_camp_details_0".equals(tag)) {
          return new ActivityCampDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_camp_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCAMPSTARS: {
        if ("layout/activity_camp_stars_0".equals(tag)) {
          return new ActivityCampStarsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_camp_stars is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCAMPAIGNDETAILS: {
        if ("layout/activity_campaign_details_0".equals(tag)) {
          return new ActivityCampaignDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_campaign_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCHAT: {
        if ("layout/activity_chat_0".equals(tag)) {
          return new ActivityChatBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_chat is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCHATMESSAGES: {
        if ("layout/activity_chat_messages_0".equals(tag)) {
          return new ActivityChatMessagesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_chat_messages is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCHOOSEACCOUNT: {
        if ("layout/activity_choose_account_0".equals(tag)) {
          return new ActivityChooseAccountBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_choose_account is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCHOOSEPAYMENTMETHOD: {
        if ("layout/activity_choose_payment_method_0".equals(tag)) {
          return new ActivityChoosePaymentMethodBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_choose_payment_method is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCLIENTMORE: {
        if ("layout/activity_client_more_0".equals(tag)) {
          return new ActivityClientMoreBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_client_more is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCLIENTPROFILE: {
        if ("layout/activity_client_profile_0".equals(tag)) {
          return new ActivityClientProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_client_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCLIENTREVIEW: {
        if ("layout/activity_client_review_0".equals(tag)) {
          return new ActivityClientReviewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_client_review is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCLIENTSETTING: {
        if ("layout/activity_client_setting_0".equals(tag)) {
          return new ActivityClientSettingBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_client_setting is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYCLIENTSETTINGS: {
        if ("layout/activity_client_settings_0".equals(tag)) {
          return new ActivityClientSettingsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_client_settings is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYDEPOSIT: {
        if ("layout/activity_deposit_0".equals(tag)) {
          return new ActivityDepositBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_deposit is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYDEPOSITFUNDSCOPY: {
        if ("layout/activity_deposit_funds_copy_0".equals(tag)) {
          return new ActivityDepositFundsCopyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_deposit_funds_copy is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYEDITPAYPAL: {
        if ("layout/activity_edit_paypal_0".equals(tag)) {
          return new ActivityEditPaypalBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_edit_paypal is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYEMAILVERIFY: {
        if ("layout/activity_email_verify_0".equals(tag)) {
          return new ActivityEmailVerifyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_email_verify is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYEXPERTFILTER: {
        if ("layout/activity_expert_filter_0".equals(tag)) {
          return new ActivityExpertFilterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_expert_filter is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYEXPERTGIGFILTER: {
        if ("layout/activity_expert_gig_filter_0".equals(tag)) {
          return new ActivityExpertGigFilterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_expert_gig_filter is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYFINDEXPERT: {
        if ("layout/activity_find_expert_0".equals(tag)) {
          return new ActivityFindExpertBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_find_expert is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYFREELANCERPROFILE: {
        if ("layout/activity_freelancer_profile_0".equals(tag)) {
          return new ActivityFreelancerProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_freelancer_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYGETDISCOUNT: {
        if ("layout/activity_get_discount_0".equals(tag)) {
          return new ActivityGetDiscountBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_get_discount is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYGIGDETAILS: {
        if ("layout/activity_gig_details_0".equals(tag)) {
          return new ActivityGigDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_gig_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYGIGEXTRAS: {
        if ("layout/activity_gig_extras_0".equals(tag)) {
          return new ActivityGigExtrasBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_gig_extras is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYHIRE: {
        if ("layout/activity_hire_0".equals(tag)) {
          return new ActivityHireBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_hire is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYHOME: {
        if ("layout/activity_home_0".equals(tag)) {
          return new ActivityHomeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_home is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYHOWITWORKS: {
        if ("layout/activity_how_it_works_0".equals(tag)) {
          return new ActivityHowItWorksBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_how_it_works is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYINFSERVICE: {
        if ("layout/activity_inf_service_0".equals(tag)) {
          return new ActivityInfServiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_inf_service is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYINFLPROFILEALL: {
        if ("layout/activity_infl_profile_all_0".equals(tag)) {
          return new ActivityInflProfileAllBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_infl_profile_all is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYINFLUENCERFILTER: {
        if ("layout/activity_influencer_filter_0".equals(tag)) {
          return new ActivityInfluencerFilterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_influencer_filter is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYINFLUENCERPROFILE: {
        if ("layout/activity_influencer_profile_0".equals(tag)) {
          return new ActivityInfluencerProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_influencer_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYINFLUENCERPROFILECOPY: {
        if ("layout/activity_influencer_profile_copy_0".equals(tag)) {
          return new ActivityInfluencerProfileCopyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_influencer_profile_copy is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYJOBPOSTTITLE: {
        if ("layout/activity_job_post_title_0".equals(tag)) {
          return new ActivityJobPostTitleBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_job_post_title is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYLAWYERHOME: {
        if ("layout/activity_lawyer_home_0".equals(tag)) {
          return new ActivityLawyerHomeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_lawyer_home is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYLAWYERHOMENEW: {
        if ("layout/activity_lawyer_home_new_0".equals(tag)) {
          return new ActivityLawyerHomeNewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_lawyer_home_new is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYLOGINSIGNUP: {
        if ("layout/activity_login_sign_up_0".equals(tag)) {
          return new ActivityLoginSignUpBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_login_sign_up is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMAIN: {
        if ("layout/activity_main_0".equals(tag)) {
          return new ActivityMainBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMAINTAINANCE: {
        if ("layout/activity_maintainance_0".equals(tag)) {
          return new ActivityMaintainanceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_maintainance is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding1(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_ACTIVITYMILESTONE: {
        if ("layout/activity_milestone_0".equals(tag)) {
          return new ActivityMilestoneBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_milestone is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMYCAMPAIGN: {
        if ("layout/activity_my_campaign_0".equals(tag)) {
          return new ActivityMyCampaignBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_my_campaign is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMYINVOICES: {
        if ("layout/activity_my_invoices_0".equals(tag)) {
          return new ActivityMyInvoicesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_my_invoices is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMYORDERS: {
        if ("layout/activity_my_orders_0".equals(tag)) {
          return new ActivityMyOrdersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_my_orders is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMYPROFILE: {
        if ("layout/activity_my_profile_0".equals(tag)) {
          return new ActivityMyProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_my_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYMYPROJECTS: {
        if ("layout/activity_my_projects_0".equals(tag)) {
          return new ActivityMyProjectsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_my_projects is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYNEWPOLICY: {
        if ("layout/activity_new_policy_0".equals(tag)) {
          return new ActivityNewPolicyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_new_policy is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYNOTIFICATION: {
        if ("layout/activity_notification_0".equals(tag)) {
          return new ActivityNotificationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_notification is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYOFFERSUMMARY: {
        if ("layout/activity_offer_summary_0".equals(tag)) {
          return new ActivityOfferSummaryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_offer_summary is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYOFFERTITLE: {
        if ("layout/activity_offer_title_0".equals(tag)) {
          return new ActivityOfferTitleBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_offer_title is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYORDERDETAILS: {
        if ("layout/activity_order_details_0".equals(tag)) {
          return new ActivityOrderDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_order_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYOTP: {
        if ("layout/activity_otp_0".equals(tag)) {
          return new ActivityOtpBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_otp is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYOTPVERIFY: {
        if ("layout/activity_otp_verify_0".equals(tag)) {
          return new ActivityOtpVerifyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_otp_verify is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPARTNERABOUT: {
        if ("layout/activity_partner_about_0".equals(tag)) {
          return new ActivityPartnerAboutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_partner_about is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPARTNERPROFILE: {
        if ("layout/activity_partner_profile_0".equals(tag)) {
          return new ActivityPartnerProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_partner_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPARTNERWITHUS: {
        if ("layout/activity_partner_with_us_0".equals(tag)) {
          return new ActivityPartnerWithUsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_partner_with_us is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPAYMENT: {
        if ("layout/activity_payment_0".equals(tag)) {
          return new ActivityPaymentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_payment is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPAYMENTNEW: {
        if ("layout/activity_payment_new_0".equals(tag)) {
          return new ActivityPaymentNewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_payment_new is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPHONEVERIFY: {
        if ("layout/activity_phone_verify_0".equals(tag)) {
          return new ActivityPhoneVerifyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_phone_verify is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPOSTJOB: {
        if ("layout/activity_post_job_0".equals(tag)) {
          return new ActivityPostJobBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_post_job is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPOSTJOBNEW: {
        if ("layout/activity_post_job_new_0".equals(tag)) {
          return new ActivityPostJobNewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_post_job_new is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPRIVATEINFO: {
        if ("layout/activity_private_info_0".equals(tag)) {
          return new ActivityPrivateInfoBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_private_info is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPROFILEINFO: {
        if ("layout/activity_profile_info_0".equals(tag)) {
          return new ActivityProfileInfoBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_profile_info is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPROFILESTARS: {
        if ("layout/activity_profile_stars_0".equals(tag)) {
          return new ActivityProfileStarsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_profile_stars is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPROJECTDETAILS: {
        if ("layout/activity_project_details_0".equals(tag)) {
          return new ActivityProjectDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_project_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPUBLICPROFILE: {
        if ("layout/activity_public_profile_0".equals(tag)) {
          return new ActivityPublicProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_public_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSEARCHTAGS: {
        if ("layout/activity_search_tags_0".equals(tag)) {
          return new ActivitySearchTagsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_search_tags is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSELECTACCOUNT: {
        if ("layout/activity_select_account_0".equals(tag)) {
          return new ActivitySelectAccountBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_select_account is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSELECTEXPERTISE: {
        if ("layout/activity_select_expertise_0".equals(tag)) {
          return new ActivitySelectExpertiseBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_select_expertise is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSELECTFREELANCER: {
        if ("layout/activity_select_freelancer_0".equals(tag)) {
          return new ActivitySelectFreelancerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_select_freelancer is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSELLERSSERVICE: {
        if ("layout/activity_sellers_service_0".equals(tag)) {
          return new ActivitySellersServiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_sellers_service is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSERVICE: {
        if ("layout/activity_service_0".equals(tag)) {
          return new ActivityServiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_service is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSERVICESELLERSSEARCH: {
        if ("layout/activity_service_sellers_search_0".equals(tag)) {
          return new ActivityServiceSellersSearchBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_service_sellers_search is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSERVICES: {
        if ("layout/activity_services_0".equals(tag)) {
          return new ActivityServicesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_services is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSHAREDISCOUNT: {
        if ("layout/activity_share_discount_0".equals(tag)) {
          return new ActivityShareDiscountBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_share_discount is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSPLASH: {
        if ("layout/activity_splash_0".equals(tag)) {
          return new ActivitySplashBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_splash is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYUPDATELOCATION: {
        if ("layout/activity_update_location_0".equals(tag)) {
          return new ActivityUpdateLocationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_update_location is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYUPDATEPASSWORD: {
        if ("layout/activity_update_password_0".equals(tag)) {
          return new ActivityUpdatePasswordBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_update_password is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYUSERNAME: {
        if ("layout/activity_username_0".equals(tag)) {
          return new ActivityUsernameBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_username is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYVERIFICATION: {
        if ("layout/activity_verification_0".equals(tag)) {
          return new ActivityVerificationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_verification is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYVERIFYPAYMENT: {
        if ("layout/activity_verify_payment_0".equals(tag)) {
          return new ActivityVerifyPaymentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_verify_payment is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYVIEWALLGIG: {
        if ("layout/activity_view_all_gig_0".equals(tag)) {
          return new ActivityViewAllGigBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_view_all_gig is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYWALLET: {
        if ("layout/activity_wallet_0".equals(tag)) {
          return new ActivityWalletBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_wallet is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYWALLETADDBALANCE: {
        if ("layout/activity_wallet_add_balance_0".equals(tag)) {
          return new ActivityWalletAddBalanceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_wallet_add_balance is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYWEBVIEW: {
        if ("layout/activity_web_view_0".equals(tag)) {
          return new ActivityWebViewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_web_view is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYWHATWEDO: {
        if ("layout/activity_what_we_do_0".equals(tag)) {
          return new ActivityWhatWeDoBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_what_we_do is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYWITHDRAWMONEY: {
        if ("layout/activity_withdraw_money_0".equals(tag)) {
          return new ActivityWithdrawMoneyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_withdraw_money is invalid. Received: " + tag);
      }
      case  LAYOUT_CHATMOREOPTIONS: {
        if ("layout/chat_more_options_0".equals(tag)) {
          return new ChatMoreOptionsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for chat_more_options is invalid. Received: " + tag);
      }
      case  LAYOUT_CHIP: {
        if ("layout/chip_0".equals(tag)) {
          return new ChipBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for chip is invalid. Received: " + tag);
      }
      case  LAYOUT_CONTENTADDCARD: {
        if ("layout/content_addcard_0".equals(tag)) {
          return new ContentAddcardBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for content_addcard is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding2(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_CONTENTCARDLIST: {
        if ("layout/content_card_list_0".equals(tag)) {
          return new ContentCardListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for content_card_list is invalid. Received: " + tag);
      }
      case  LAYOUT_CUSTOMDIALOG: {
        if ("layout/custom_dialog_0".equals(tag)) {
          return new CustomDialogBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for custom_dialog is invalid. Received: " + tag);
      }
      case  LAYOUT_CUSTOMPARTNERABOUTVIEW: {
        if ("layout/custom_partner_about_view_0".equals(tag)) {
          return new CustomPartnerAboutViewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for custom_partner_about_view is invalid. Received: " + tag);
      }
      case  LAYOUT_CUSTOMPARTNERTEXTVIEW: {
        if ("layout/custom_partner_textview_0".equals(tag)) {
          return new CustomPartnerTextviewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for custom_partner_textview is invalid. Received: " + tag);
      }
      case  LAYOUT_CUSTOMTOAST: {
        if ("layout/custom_toast_0".equals(tag)) {
          return new CustomToastBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for custom_toast is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGADDCONTACT: {
        if ("layout/dialog_add_contact_0".equals(tag)) {
          return new DialogAddContactBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_add_contact is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGBALANCEDEPOSIT: {
        if ("layout/dialog_balance_deposit_0".equals(tag)) {
          return new DialogBalanceDepositBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_balance_deposit is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGBANKTRANSFERDONE: {
        if ("layout/dialog_bank_transfer_done_0".equals(tag)) {
          return new DialogBankTransferDoneBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_bank_transfer_done is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGCAMERADOCUMENTSELECT: {
        if ("layout/dialog_camera_document_select_0".equals(tag)) {
          return new DialogCameraDocumentSelectBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_camera_document_select is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGCANCELFREELANCER: {
        if ("layout/dialog_cancel_freelancer_0".equals(tag)) {
          return new DialogCancelFreelancerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_cancel_freelancer is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGCHATNOW: {
        if ("layout/dialog_chat_now_0".equals(tag)) {
          return new DialogChatNowBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_chat_now is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGCHATSETTING: {
        if ("layout/dialog_chat_setting_0".equals(tag)) {
          return new DialogChatSettingBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_chat_setting is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGCHOOSEPLATFORM: {
        if ("layout/dialog_choose_platform_0".equals(tag)) {
          return new DialogChoosePlatformBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_choose_platform is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGCLOSEPROJECT: {
        if ("layout/dialog_close_project_0".equals(tag)) {
          return new DialogCloseProjectBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_close_project is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGCURRENCY: {
        if ("layout/dialog_currency_0".equals(tag)) {
          return new DialogCurrencyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_currency is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGCUSTOMPRICE: {
        if ("layout/dialog_custom_price_0".equals(tag)) {
          return new DialogCustomPriceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_custom_price is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGCUSTOMPRICEOPTION: {
        if ("layout/dialog_custom_price_option_0".equals(tag)) {
          return new DialogCustomPriceOptionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_custom_price_option is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGDELETEACCOUNT: {
        if ("layout/dialog_delete_account_0".equals(tag)) {
          return new DialogDeleteAccountBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_delete_account is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGDELETEPROJECT: {
        if ("layout/dialog_delete_project_0".equals(tag)) {
          return new DialogDeleteProjectBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_delete_project is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGDEPOSIT: {
        if ("layout/dialog_deposit_0".equals(tag)) {
          return new DialogDepositBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_deposit is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGDEPOSITNOTES: {
        if ("layout/dialog_deposit_notes_0".equals(tag)) {
          return new DialogDepositNotesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_deposit_notes is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGEMAILVERIFICATION: {
        if ("layout/dialog_email_verification_0".equals(tag)) {
          return new DialogEmailVerificationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_email_verification is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGENTERCVV: {
        if ("layout/dialog_enter_cvv_0".equals(tag)) {
          return new DialogEnterCvvBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_enter_cvv is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGFEEDBACK: {
        if ("layout/dialog_feedback_0".equals(tag)) {
          return new DialogFeedbackBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_feedback is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGFILEOPEN: {
        if ("layout/dialog_file_open_0".equals(tag)) {
          return new DialogFileOpenBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_file_open is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGFILEOPTIONMENU: {
        if ("layout/dialog_file_option_menu_0".equals(tag)) {
          return new DialogFileOptionMenuBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_file_option_menu is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGFORGOTPASSWORD: {
        if ("layout/dialog_forgot_password_0".equals(tag)) {
          return new DialogForgotPasswordBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_forgot_password is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGFREETRIAL: {
        if ("layout/dialog_free_trial_0".equals(tag)) {
          return new DialogFreeTrialBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_free_trial is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGITEMSELECTBLACK: {
        if ("layout/dialog_item_select_black_0".equals(tag)) {
          return new DialogItemSelectBlackBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_item_select_black is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGLANGUAGE: {
        if ("layout/dialog_language_0".equals(tag)) {
          return new DialogLanguageBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_language is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGLOCATIONDISCLOSURE: {
        if ("layout/dialog_location_disclosure_0".equals(tag)) {
          return new DialogLocationDisclosureBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_location_disclosure is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGLOCATIONSKILL: {
        if ("layout/dialog_location_skill_0".equals(tag)) {
          return new DialogLocationSkillBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_location_skill is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGLOGINNEW: {
        if ("layout/dialog_login_new_0".equals(tag)) {
          return new DialogLoginNewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_login_new is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGLOGINSIGNUP: {
        if ("layout/dialog_login_sign_up_0".equals(tag)) {
          return new DialogLoginSignUpBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_login_sign_up is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGLOGOUT: {
        if ("layout/dialog_logout_0".equals(tag)) {
          return new DialogLogoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_logout is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGMYPROFILEDETAIL: {
        if ("layout/dialog_my_profile_detail_0".equals(tag)) {
          return new DialogMyProfileDetailBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_my_profile_detail is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGNOINTERNET: {
        if ("layout/dialog_no_internet_0".equals(tag)) {
          return new DialogNoInternetBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_no_internet is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGOPENWEBSITE: {
        if ("layout/dialog_open_website_0".equals(tag)) {
          return new DialogOpenWebsiteBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_open_website is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGPAYDONE: {
        if ("layout/dialog_pay_done_0".equals(tag)) {
          return new DialogPayDoneBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_pay_done is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGPAYMENTDEPOSIT: {
        if ("layout/dialog_payment_deposit_0".equals(tag)) {
          return new DialogPaymentDepositBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_payment_deposit is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGPAYMENTDEPOSITDONE: {
        if ("layout/dialog_payment_deposit_done_0".equals(tag)) {
          return new DialogPaymentDepositDoneBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_payment_deposit_done is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGPAYMENTDONE: {
        if ("layout/dialog_payment_done_0".equals(tag)) {
          return new DialogPaymentDoneBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_payment_done is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGPOSTINGDONE: {
        if ("layout/dialog_posting_done_0".equals(tag)) {
          return new DialogPostingDoneBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_posting_done is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGPROMOCODE: {
        if ("layout/dialog_promo_code_0".equals(tag)) {
          return new DialogPromoCodeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_promo_code is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGRATEAPP: {
        if ("layout/dialog_rate_app_0".equals(tag)) {
          return new DialogRateAppBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_rate_app is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGRATEME: {
        if ("layout/dialog_rate_me_0".equals(tag)) {
          return new DialogRateMeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_rate_me is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGREFUNDPAYMENT: {
        if ("layout/dialog_refund_payment_0".equals(tag)) {
          return new DialogRefundPaymentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_refund_payment is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGREFUNDREASON: {
        if ("layout/dialog_refund_reason_0".equals(tag)) {
          return new DialogRefundReasonBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_refund_reason is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGREFUNDUSER: {
        if ("layout/dialog_refund_user_0".equals(tag)) {
          return new DialogRefundUserBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_refund_user is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGRELEASEPAYMENT: {
        if ("layout/dialog_release_payment_0".equals(tag)) {
          return new DialogReleasePaymentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_release_payment is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding3(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_DIALOGREPOSTDELETEJOB: {
        if ("layout/dialog_repost_delete_job_0".equals(tag)) {
          return new DialogRepostDeleteJobBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_repost_delete_job is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGSECURITYCODE: {
        if ("layout/dialog_security_code_0".equals(tag)) {
          return new DialogSecurityCodeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_security_code is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGSORTBYFILTER: {
        if ("layout/dialog_sort_by_filter_0".equals(tag)) {
          return new DialogSortByFilterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_sort_by_filter is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGSTARS: {
        if ("layout/dialog_stars_0".equals(tag)) {
          return new DialogStarsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_stars is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGSTORAGEDISCLOSURE: {
        if ("layout/dialog_storage_disclosure_0".equals(tag)) {
          return new DialogStorageDisclosureBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_storage_disclosure is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGTIMELINE: {
        if ("layout/dialog_timeline_0".equals(tag)) {
          return new DialogTimelineBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_timeline is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGWITHDRAW: {
        if ("layout/dialog_withdraw_0".equals(tag)) {
          return new DialogWithdrawBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_withdraw is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGZOOMPORTFOLIOIMAGE: {
        if ("layout/dialog_zoom_portfolio_image_0".equals(tag)) {
          return new DialogZoomPortfolioImageBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_zoom_portfolio_image is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTABOUTPROFILE: {
        if ("layout/fragment_about_profile_0".equals(tag)) {
          return new FragmentAboutProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_about_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTALLPOPULARLAWYER: {
        if ("layout/fragment_all_popular_lawyer_0".equals(tag)) {
          return new FragmentAllPopularLawyerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_all_popular_lawyer is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTATTACHMENT: {
        if ("layout/fragment_attachment_0".equals(tag)) {
          return new FragmentAttachmentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_attachment is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBALANCEDEPOSIT: {
        if ("layout/fragment_balance_deposit_0".equals(tag)) {
          return new FragmentBalanceDepositBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_balance_deposit is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBALANCEHISTORY: {
        if ("layout/fragment_balance_history_0".equals(tag)) {
          return new FragmentBalanceHistoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_balance_history is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTBALANCEPAYMENT: {
        if ("layout/fragment_balance_payment_0".equals(tag)) {
          return new FragmentBalancePaymentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_balance_payment is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCAMPDETAIL: {
        if ("layout/fragment_camp_detail_0".equals(tag)) {
          return new FragmentCampDetailBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_camp_detail is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCAMPPAY: {
        if ("layout/fragment_camp_pay_0".equals(tag)) {
          return new FragmentCampPayBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_camp_pay is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCAMPSTARS: {
        if ("layout/fragment_camp_stars_0".equals(tag)) {
          return new FragmentCampStarsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_camp_stars is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCHATLIST: {
        if ("layout/fragment_chat_list_0".equals(tag)) {
          return new FragmentChatListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_chat_list is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCHATMANAGER: {
        if ("layout/fragment_chat_manager_0".equals(tag)) {
          return new FragmentChatManagerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_chat_manager is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCHOOSEDEVELOPER: {
        if ("layout/fragment_choose_developer_0".equals(tag)) {
          return new FragmentChooseDeveloperBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_choose_developer is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTCHOOSESKILLS: {
        if ("layout/fragment_choose_skills_0".equals(tag)) {
          return new FragmentChooseSkillsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_choose_skills is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTDEADLINE: {
        if ("layout/fragment_deadline_0".equals(tag)) {
          return new FragmentDeadlineBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_deadline is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTDEPOSITFUNDS: {
        if ("layout/fragment_deposit_funds_0".equals(tag)) {
          return new FragmentDepositFundsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_deposit_funds is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTDESCRIBE: {
        if ("layout/fragment_describe_0".equals(tag)) {
          return new FragmentDescribeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_describe is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTEARNMONEY: {
        if ("layout/fragment_earn_money_0".equals(tag)) {
          return new FragmentEarnMoneyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_earn_money is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTENTERRATE: {
        if ("layout/fragment_enter_rate_0".equals(tag)) {
          return new FragmentEnterRateBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_enter_rate is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTINFAGENCY: {
        if ("layout/fragment_inf_agency_0".equals(tag)) {
          return new FragmentInfAgencyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_inf_agency is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTINFALL: {
        if ("layout/fragment_inf_all_0".equals(tag)) {
          return new FragmentInfAllBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_inf_all is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTINFSERVICES: {
        if ("layout/fragment_inf_services_0".equals(tag)) {
          return new FragmentInfServicesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_inf_services is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTLAWYERSERVICE: {
        if ("layout/fragment_lawyer_service_0".equals(tag)) {
          return new FragmentLawyerServiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_lawyer_service is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTLIVECHAT: {
        if ("layout/fragment_live_chat_0".equals(tag)) {
          return new FragmentLiveChatBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_live_chat is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTMYLEVEL: {
        if ("layout/fragment_my_level_0".equals(tag)) {
          return new FragmentMyLevelBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_my_level is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPAYTYPE: {
        if ("layout/fragment_pay_type_0".equals(tag)) {
          return new FragmentPayTypeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_pay_type is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPOSTJOB: {
        if ("layout/fragment_post_job_0".equals(tag)) {
          return new FragmentPostJobBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_post_job is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPROJECTDETAILS: {
        if ("layout/fragment_project_details_0".equals(tag)) {
          return new FragmentProjectDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_project_details is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPROJECTPAYMENTNEW: {
        if ("layout/fragment_project_payment_new_0".equals(tag)) {
          return new FragmentProjectPaymentNewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_project_payment_new is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPROJECTRATE: {
        if ("layout/fragment_project_rate_0".equals(tag)) {
          return new FragmentProjectRateBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_project_rate is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPROJECTSTATUS: {
        if ("layout/fragment_project_status_0".equals(tag)) {
          return new FragmentProjectStatusBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_project_status is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPROJECTSUBMIT: {
        if ("layout/fragment_project_submit_0".equals(tag)) {
          return new FragmentProjectSubmitBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_project_submit is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTPROJECTSLIST: {
        if ("layout/fragment_projects_list_0".equals(tag)) {
          return new FragmentProjectsListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_projects_list is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTREVIEWSPROFILE: {
        if ("layout/fragment_reviews_profile_0".equals(tag)) {
          return new FragmentReviewsProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_reviews_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTSELECTRATE: {
        if ("layout/fragment_select_rate_0".equals(tag)) {
          return new FragmentSelectRateBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_select_rate is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTSELECTSERVICE: {
        if ("layout/fragment_select_service_0".equals(tag)) {
          return new FragmentSelectServiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_select_service is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTSKILLPROFILE: {
        if ("layout/fragment_skill_profile_0".equals(tag)) {
          return new FragmentSkillProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_skill_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTWANTTOPAY: {
        if ("layout/fragment_want_to_pay_0".equals(tag)) {
          return new FragmentWantToPayBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_want_to_pay is invalid. Received: " + tag);
      }
      case  LAYOUT_FRAGMENTWIN: {
        if ("layout/fragment_win_0".equals(tag)) {
          return new FragmentWinBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for fragment_win is invalid. Received: " + tag);
      }
      case  LAYOUT_HIREGRIDITEM: {
        if ("layout/hire_grid_item_0".equals(tag)) {
          return new HireGridItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for hire_grid_item is invalid. Received: " + tag);
      }
      case  LAYOUT_HOMEGRIDITEM: {
        if ("layout/home_grid_item_0".equals(tag)) {
          return new HomeGridItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for home_grid_item is invalid. Received: " + tag);
      }
      case  LAYOUT_HOMELISTITEM: {
        if ("layout/home_list_item_0".equals(tag)) {
          return new HomeListItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for home_list_item is invalid. Received: " + tag);
      }
      case  LAYOUT_HOMEPAGEITEM: {
        if ("layout/home_page_item_0".equals(tag)) {
          return new HomePageItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for home_page_item is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding4(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_ITEMACCOUNT: {
        if ("layout/item_account_0".equals(tag)) {
          return new ItemAccountBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_account is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMBANNERLAWYER: {
        if ("layout/item_banner_lawyer_0".equals(tag)) {
          return new ItemBannerLawyerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_banner_lawyer is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCAMPAIGNINPROGRESS: {
        if ("layout/item_campaign_inprogress_0".equals(tag)) {
          return new ItemCampaignInprogressBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_campaign_inprogress is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCAMPAIGNSTARS: {
        if ("layout/item_campaign_stars_0".equals(tag)) {
          return new ItemCampaignStarsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_campaign_stars is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCARDLIST: {
        if ("layout/item_card_list_0".equals(tag)) {
          return new ItemCardListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_card_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCARDVIEW: {
        if ("layout/item_card_view_0".equals(tag)) {
          return new ItemCardViewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_card_view is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCATEGORYLIST: {
        if ("layout/item_category_list_0".equals(tag)) {
          return new ItemCategoryListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_category_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCHATLIST: {
        if ("layout/item_chat_list_0".equals(tag)) {
          return new ItemChatListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_chat_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCHATMESSAGES: {
        if ("layout/item_chat_messages_0".equals(tag)) {
          return new ItemChatMessagesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_chat_messages is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCHATMSG: {
        if ("layout/item_chat_msg_0".equals(tag)) {
          return new ItemChatMsgBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_chat_msg is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCHIPVIEW: {
        if ("layout/item_chip_view_0".equals(tag)) {
          return new ItemChipViewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_chip_view is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCHOOSEACCOUNT: {
        if ("layout/item_choose_account_0".equals(tag)) {
          return new ItemChooseAccountBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_choose_account is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCLIENTREVIEW: {
        if ("layout/item_client_review_0".equals(tag)) {
          return new ItemClientReviewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_client_review is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCUSTOMGIGDETAILS: {
        if ("layout/item_custom_gig_details_0".equals(tag)) {
          return new ItemCustomGigDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_custom_gig_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCUSTOMGIGPROJECTDETAILS: {
        if ("layout/item_custom_gig_project_details_0".equals(tag)) {
          return new ItemCustomGigProjectDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_custom_gig_project_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMCUSTOMPRICE: {
        if ("layout/item_custom_price_0".equals(tag)) {
          return new ItemCustomPriceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_custom_price is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMEXPERTLIST: {
        if ("layout/item_expert_list_0".equals(tag)) {
          return new ItemExpertListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_expert_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMEXPERTLISTPLACEHOLDER: {
        if ("layout/item_expert_list_placeholder_0".equals(tag)) {
          return new ItemExpertListPlaceholderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_expert_list_placeholder is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMFILESDESC: {
        if ("layout/item_files_desc_0".equals(tag)) {
          return new ItemFilesDescBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_files_desc is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMFILTERSERVICE: {
        if ("layout/item_filter_service_0".equals(tag)) {
          return new ItemFilterServiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_filter_service is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMFOLLWERSLIST: {
        if ("layout/item_follwers_list_0".equals(tag)) {
          return new ItemFollwersListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_follwers_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMGIGDETAILPAGER: {
        if ("layout/item_gig_detail_pager_0".equals(tag)) {
          return new ItemGigDetailPagerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_gig_detail_pager is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMGIGDETAILS: {
        if ("layout/item_gig_details_0".equals(tag)) {
          return new ItemGigDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_gig_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMGIGDETAILSPLACEHOLDER: {
        if ("layout/item_gig_details_placeholder_0".equals(tag)) {
          return new ItemGigDetailsPlaceholderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_gig_details_placeholder is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMGIGEXTRAS: {
        if ("layout/item_gig_extras_0".equals(tag)) {
          return new ItemGigExtrasBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_gig_extras is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMGIGHOME: {
        if ("layout/item_gig_home_0".equals(tag)) {
          return new ItemGigHomeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_gig_home is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMGIGHOMENEW: {
        if ("layout/item_gig_home_new_0".equals(tag)) {
          return new ItemGigHomeNewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_gig_home_new is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMGIGPLACEHOLDER: {
        if ("layout/item_gig_placeholder_0".equals(tag)) {
          return new ItemGigPlaceholderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_gig_placeholder is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMHIRE: {
        if ("layout/item_hire_0".equals(tag)) {
          return new ItemHireBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_hire is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMHOME: {
        if ("layout/item_home_0".equals(tag)) {
          return new ItemHomeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_home is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMHOMECATEGORY: {
        if ("layout/item_home_category_0".equals(tag)) {
          return new ItemHomeCategoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_home_category is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMHOMECATEGORYLAWYER: {
        if ("layout/item_home_category_lawyer_0".equals(tag)) {
          return new ItemHomeCategoryLawyerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_home_category_lawyer is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMHOMEFOLLOWER: {
        if ("layout/item_home_follower_0".equals(tag)) {
          return new ItemHomeFollowerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_home_follower is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMHOMEISTOP: {
        if ("layout/item_home_is_top_0".equals(tag)) {
          return new ItemHomeIsTopBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_home_is_top is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMINCOMEBALANE: {
        if ("layout/item_income_balane_0".equals(tag)) {
          return new ItemIncomeBalaneBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_income_balane is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMINFREVIEWS: {
        if ("layout/item_inf_reviews_0".equals(tag)) {
          return new ItemInfReviewsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_inf_reviews is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMINFSERVICE: {
        if ("layout/item_inf_service_0".equals(tag)) {
          return new ItemInfServiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_inf_service is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMINFSERVICENEW: {
        if ("layout/item_inf_service_new_0".equals(tag)) {
          return new ItemInfServiceNewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_inf_service_new is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMINFSERVICES: {
        if ("layout/item_inf_services_0".equals(tag)) {
          return new ItemInfServicesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_inf_services is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMINFSTORE: {
        if ("layout/item_inf_store_0".equals(tag)) {
          return new ItemInfStoreBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_inf_store is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMJOBTITLE: {
        if ("layout/item_job_title_0".equals(tag)) {
          return new ItemJobTitleBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_job_title is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMLANGUAGEAGENTS: {
        if ("layout/item_language_agents_0".equals(tag)) {
          return new ItemLanguageAgentsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_language_agents is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMLISTFILES: {
        if ("layout/item_list_files_0".equals(tag)) {
          return new ItemListFilesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_list_files is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMLISTFILESSURVEY: {
        if ("layout/item_list_files_survey_0".equals(tag)) {
          return new ItemListFilesSurveyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_list_files_survey is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMMYINVOICE: {
        if ("layout/item_my_invoice_0".equals(tag)) {
          return new ItemMyInvoiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_my_invoice is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMMYSTORE: {
        if ("layout/item_my_store_0".equals(tag)) {
          return new ItemMyStoreBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_my_store is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMNOTIFICATION: {
        if ("layout/item_notification_0".equals(tag)) {
          return new ItemNotificationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_notification is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMNOTIFICATIONPH: {
        if ("layout/item_notification_ph_0".equals(tag)) {
          return new ItemNotificationPhBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_notification_ph is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMORDERSTARS: {
        if ("layout/item_order_stars_0".equals(tag)) {
          return new ItemOrderStarsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_order_stars is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMORDERS: {
        if ("layout/item_orders_0".equals(tag)) {
          return new ItemOrdersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_orders is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding5(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_ITEMORDERSPH: {
        if ("layout/item_orders_ph_0".equals(tag)) {
          return new ItemOrdersPhBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_orders_ph is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMORDERSTEXT: {
        if ("layout/item_orders_text_0".equals(tag)) {
          return new ItemOrdersTextBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_orders_text is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPARTNERANSWER: {
        if ("layout/item_partner_answer_0".equals(tag)) {
          return new ItemPartnerAnswerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_partner_answer is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPARTNERS: {
        if ("layout/item_partners_0".equals(tag)) {
          return new ItemPartnersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_partners is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPLATFORM: {
        if ("layout/item_platform_0".equals(tag)) {
          return new ItemPlatformBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_platform is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPLATFORMIMG: {
        if ("layout/item_platform_img_0".equals(tag)) {
          return new ItemPlatformImgBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_platform_img is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPLATFORMSELECTION: {
        if ("layout/item_platform_selection_0".equals(tag)) {
          return new ItemPlatformSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_platform_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPOLICY: {
        if ("layout/item_policy_0".equals(tag)) {
          return new ItemPolicyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_policy is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPOPULAR: {
        if ("layout/item_popular_0".equals(tag)) {
          return new ItemPopularBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_popular is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPOPULARPLACEHOLDER: {
        if ("layout/item_popular_placeholder_0".equals(tag)) {
          return new ItemPopularPlaceholderBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_popular_placeholder is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPORTFOLIOLIST: {
        if ("layout/item_portfolio_list_0".equals(tag)) {
          return new ItemPortfolioListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_portfolio_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPORTFOLIOLISTPH: {
        if ("layout/item_portfolio_list_ph_0".equals(tag)) {
          return new ItemPortfolioListPhBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_portfolio_list_ph is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPORTFOLIOSMALL: {
        if ("layout/item_portfolio_small_0".equals(tag)) {
          return new ItemPortfolioSmallBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_portfolio_small is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPROFILEPRODUCT: {
        if ("layout/item_profile_product_0".equals(tag)) {
          return new ItemProfileProductBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_profile_product is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPROJECTSLIST: {
        if ("layout/item_projects_list_0".equals(tag)) {
          return new ItemProjectsListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_projects_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPROPOSALFREELANCERLIST: {
        if ("layout/item_proposal_freelancer_list_0".equals(tag)) {
          return new ItemProposalFreelancerListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_proposal_freelancer_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMPROPOSALLIST: {
        if ("layout/item_proposal_list_0".equals(tag)) {
          return new ItemProposalListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_proposal_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMREFERRALHISTORY: {
        if ("layout/item_referral_history_0".equals(tag)) {
          return new ItemReferralHistoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_referral_history is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMREVIEWS: {
        if ("layout/item_reviews_0".equals(tag)) {
          return new ItemReviewsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_reviews is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMREVIEWSPH: {
        if ("layout/item_reviews_ph_0".equals(tag)) {
          return new ItemReviewsPhBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_reviews_ph is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSELECTFULL: {
        if ("layout/item_select_full_0".equals(tag)) {
          return new ItemSelectFullBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_select_full is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSELECTLANGUAGE: {
        if ("layout/item_select_language_0".equals(tag)) {
          return new ItemSelectLanguageBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_select_language is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSELECTRATE: {
        if ("layout/item_select_rate_0".equals(tag)) {
          return new ItemSelectRateBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_select_rate is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSELECTSERVICE: {
        if ("layout/item_select_service_0".equals(tag)) {
          return new ItemSelectServiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_select_service is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSELECTEDSTARS: {
        if ("layout/item_selected_stars_0".equals(tag)) {
          return new ItemSelectedStarsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_selected_stars is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSERVICE: {
        if ("layout/item_service_0".equals(tag)) {
          return new ItemServiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_service is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSERVICEPLATFORM: {
        if ("layout/item_service_platform_0".equals(tag)) {
          return new ItemServicePlatformBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_service_platform is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSERVICESELLERS: {
        if ("layout/item_service_sellers_0".equals(tag)) {
          return new ItemServiceSellersBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_service_sellers is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSINGLEMULTISELECTION: {
        if ("layout/item_singlemultiselection_0".equals(tag)) {
          return new ItemSinglemultiselectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_singlemultiselection is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSKILLFILTER: {
        if ("layout/item_skill_filter_0".equals(tag)) {
          return new ItemSkillFilterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_skill_filter is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSKILLS: {
        if ("layout/item_skills_0".equals(tag)) {
          return new ItemSkillsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_skills is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSKILLSEDIT: {
        if ("layout/item_skills_edit_0".equals(tag)) {
          return new ItemSkillsEditBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_skills_edit is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSKILLSPOST: {
        if ("layout/item_skills_post_0".equals(tag)) {
          return new ItemSkillsPostBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_skills_post is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSM: {
        if ("layout/item_sm_0".equals(tag)) {
          return new ItemSmBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_sm is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSOCIAL: {
        if ("layout/item_social_0".equals(tag)) {
          return new ItemSocialBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_social is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSOCIALGIG: {
        if ("layout/item_social_gig_0".equals(tag)) {
          return new ItemSocialGigBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_social_gig is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSOCIALGIGDETAILS: {
        if ("layout/item_social_gig_details_0".equals(tag)) {
          return new ItemSocialGigDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_social_gig_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSOCIALINFSERV: {
        if ("layout/item_social_inf_serv_0".equals(tag)) {
          return new ItemSocialInfServBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_social_inf_serv is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSOCIALMEDIA: {
        if ("layout/item_social_media_0".equals(tag)) {
          return new ItemSocialMediaBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_social_media is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSOCIALMEDIAPROFILE: {
        if ("layout/item_social_media_profile_0".equals(tag)) {
          return new ItemSocialMediaProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_social_media_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSOCIALSERVICE: {
        if ("layout/item_social_service_0".equals(tag)) {
          return new ItemSocialServiceBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_social_service is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSTARS: {
        if ("layout/item_stars_0".equals(tag)) {
          return new ItemStarsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_stars is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSTARSPLATFORM: {
        if ("layout/item_stars_platform_0".equals(tag)) {
          return new ItemStarsPlatformBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_stars_platform is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSUBMITTEDFILES: {
        if ("layout/item_submitted_files_0".equals(tag)) {
          return new ItemSubmittedFilesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_submitted_files is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMTAGVIEW: {
        if ("layout/item_tag_view_0".equals(tag)) {
          return new ItemTagViewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_tag_view is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMUPLOADEDFILES: {
        if ("layout/item_uploaded_files_0".equals(tag)) {
          return new ItemUploadedFilesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_uploaded_files is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMUSERREHIRE: {
        if ("layout/item_user_rehire_0".equals(tag)) {
          return new ItemUserRehireBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_user_rehire is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMVERIFICATION: {
        if ("layout/item_verification_0".equals(tag)) {
          return new ItemVerificationBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_verification is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMVERIFIEDWITH: {
        if ("layout/item_verified_with_0".equals(tag)) {
          return new ItemVerifiedWithBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_verified_with is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMVIEWALLCATEGORY: {
        if ("layout/item_view_all_category_0".equals(tag)) {
          return new ItemViewAllCategoryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_view_all_category is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding6(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_ITEMWALLET: {
        if ("layout/item_wallet_0".equals(tag)) {
          return new ItemWalletBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_wallet is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMWORKWITH: {
        if ("layout/item_workwith_0".equals(tag)) {
          return new ItemWorkwithBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_workwith is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMYOUTUBEPROFILE: {
        if ("layout/item_youtube_profile_0".equals(tag)) {
          return new ItemYoutubeProfileBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_youtube_profile is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYIMAGE: {
        if ("layout/lay_image_0".equals(tag)) {
          return new LayImageBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for lay_image is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYTEXT: {
        if ("layout/lay_text_0".equals(tag)) {
          return new LayTextBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for lay_text is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTHOMEHOWWORK: {
        if ("layout/layout_home_how_work_0".equals(tag)) {
          return new LayoutHomeHowWorkBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_home_how_work is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTMILESTONEPAYMENT: {
        if ("layout/layout_milestone_payment_0".equals(tag)) {
          return new LayoutMilestonePaymentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_milestone_payment is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTTEXTVIEW: {
        if ("layout/layout_textview_0".equals(tag)) {
          return new LayoutTextviewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_textview is invalid. Received: " + tag);
      }
      case  LAYOUT_NODATALAYOUT: {
        if ("layout/no_data_layout_0".equals(tag)) {
          return new NoDataLayoutBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for no_data_layout is invalid. Received: " + tag);
      }
      case  LAYOUT_TIMELINEITEM: {
        if ("layout/timeline_item_0".equals(tag)) {
          return new TimelineItemBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for timeline_item is invalid. Received: " + tag);
      }
      case  LAYOUT_TOOLBARBACK: {
        if ("layout/toolbar_back_0".equals(tag)) {
          return new ToolbarBackBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for toolbar_back is invalid. Received: " + tag);
      }
      case  LAYOUT_TOOLBARO: {
        if ("layout/toolbar_o_0".equals(tag)) {
          return new ToolbarOBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for toolbar_o is invalid. Received: " + tag);
      }
      case  LAYOUT_TOOLBARPROGRESSNEXT: {
        if ("layout/toolbar_progress_next_0".equals(tag)) {
          return new ToolbarProgressNextBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for toolbar_progress_next is invalid. Received: " + tag);
      }
      case  LAYOUT_TOOLBARSAVE: {
        if ("layout/toolbar_save_0".equals(tag)) {
          return new ToolbarSaveBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for toolbar_save is invalid. Received: " + tag);
      }
      case  LAYOUT_TOOLBARTITLE: {
        if ("layout/toolbar_title_0".equals(tag)) {
          return new ToolbarTitleBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for toolbar_title is invalid. Received: " + tag);
      }
      case  LAYOUT_VIEWAGENCY: {
        if ("layout/view_agency_0".equals(tag)) {
          return new ViewAgencyBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for view_agency is invalid. Received: " + tag);
      }
      case  LAYOUT_VIEWMYSTORE: {
        if ("layout/view_my_store_0".equals(tag)) {
          return new ViewMyStoreBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for view_my_store is invalid. Received: " + tag);
      }
      case  LAYOUT_VIEWOVERVIEW: {
        if ("layout/view_overview_0".equals(tag)) {
          return new ViewOverviewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for view_overview is invalid. Received: " + tag);
      }
      case  LAYOUT_VIEWPARTNER: {
        if ("layout/view_partner_0".equals(tag)) {
          return new ViewPartnerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for view_partner is invalid. Received: " + tag);
      }
      case  LAYOUT_VIEWPORTFOLIO: {
        if ("layout/view_portfolio_0".equals(tag)) {
          return new ViewPortfolioBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for view_portfolio is invalid. Received: " + tag);
      }
      case  LAYOUT_VIEWSERVICES: {
        if ("layout/view_services_0".equals(tag)) {
          return new ViewServicesBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for view_services is invalid. Received: " + tag);
      }
      case  LAYOUT_VIEWSOCIALMEDIA: {
        if ("layout/view_social_media_0".equals(tag)) {
          return new ViewSocialMediaBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for view_social_media is invalid. Received: " + tag);
      }
      case  LAYOUT_VIEWWORKWITH: {
        if ("layout/view_workwith_0".equals(tag)) {
          return new ViewWorkwithBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for view_workwith is invalid. Received: " + tag);
      }
      case  LAYOUT_VIEWYOUTUBE: {
        if ("layout/view_youtube_0".equals(tag)) {
          return new ViewYoutubeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for view_youtube is invalid. Received: " + tag);
      }
      case  LAYOUT_VWLAYOUTITEMAUDIOPICK: {
        if ("layout/vw_layout_item_audio_pick_0".equals(tag)) {
          return new VwLayoutItemAudioPickBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vw_layout_item_audio_pick is invalid. Received: " + tag);
      }
      case  LAYOUT_VWLAYOUTITEMFOLDERLIST: {
        if ("layout/vw_layout_item_folder_list_0".equals(tag)) {
          return new VwLayoutItemFolderListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for vw_layout_item_folder_list is invalid. Received: " + tag);
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      // find which method will have it. -1 is necessary becausefirst id starts with 1;
      int methodIndex = (localizedLayoutId - 1) / 50;
      switch(methodIndex) {
        case 0: {
          return internalGetViewDataBinding0(component, view, localizedLayoutId, tag);
        }
        case 1: {
          return internalGetViewDataBinding1(component, view, localizedLayoutId, tag);
        }
        case 2: {
          return internalGetViewDataBinding2(component, view, localizedLayoutId, tag);
        }
        case 3: {
          return internalGetViewDataBinding3(component, view, localizedLayoutId, tag);
        }
        case 4: {
          return internalGetViewDataBinding4(component, view, localizedLayoutId, tag);
        }
        case 5: {
          return internalGetViewDataBinding5(component, view, localizedLayoutId, tag);
        }
        case 6: {
          return internalGetViewDataBinding6(component, view, localizedLayoutId, tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(326);

    static {
      sKeys.put("layout/activity_account_delete_0", com.nojom.client.R.layout.activity_account_delete);
      sKeys.put("layout/activity_add_card_0", com.nojom.client.R.layout.activity_add_card);
      sKeys.put("layout/activity_add_describe_0", com.nojom.client.R.layout.activity_add_describe);
      sKeys.put("layout/activity_add_money_0", com.nojom.client.R.layout.activity_add_money);
      sKeys.put("layout/activity_add_paypal_0", com.nojom.client.R.layout.activity_add_paypal);
      sKeys.put("layout/activity_add_star_0", com.nojom.client.R.layout.activity_add_star);
      sKeys.put("layout/activity_add_survey_0", com.nojom.client.R.layout.activity_add_survey);
      sKeys.put("layout/activity_add_survey_submit_0", com.nojom.client.R.layout.activity_add_survey_submit);
      sKeys.put("layout/activity_auto_palce_location_0", com.nojom.client.R.layout.activity_auto_palce_location);
      sKeys.put("layout/activity_balance_0", com.nojom.client.R.layout.activity_balance);
      sKeys.put("layout/activity_bank_transfer_0", com.nojom.client.R.layout.activity_bank_transfer);
      sKeys.put("layout/activity_caller_0", com.nojom.client.R.layout.activity_caller);
      sKeys.put("layout/activity_camp_data_0", com.nojom.client.R.layout.activity_camp_data);
      sKeys.put("layout/activity_camp_details_0", com.nojom.client.R.layout.activity_camp_details);
      sKeys.put("layout/activity_camp_stars_0", com.nojom.client.R.layout.activity_camp_stars);
      sKeys.put("layout/activity_campaign_details_0", com.nojom.client.R.layout.activity_campaign_details);
      sKeys.put("layout/activity_chat_0", com.nojom.client.R.layout.activity_chat);
      sKeys.put("layout/activity_chat_messages_0", com.nojom.client.R.layout.activity_chat_messages);
      sKeys.put("layout/activity_choose_account_0", com.nojom.client.R.layout.activity_choose_account);
      sKeys.put("layout/activity_choose_payment_method_0", com.nojom.client.R.layout.activity_choose_payment_method);
      sKeys.put("layout/activity_client_more_0", com.nojom.client.R.layout.activity_client_more);
      sKeys.put("layout/activity_client_profile_0", com.nojom.client.R.layout.activity_client_profile);
      sKeys.put("layout/activity_client_review_0", com.nojom.client.R.layout.activity_client_review);
      sKeys.put("layout/activity_client_setting_0", com.nojom.client.R.layout.activity_client_setting);
      sKeys.put("layout/activity_client_settings_0", com.nojom.client.R.layout.activity_client_settings);
      sKeys.put("layout/activity_deposit_0", com.nojom.client.R.layout.activity_deposit);
      sKeys.put("layout/activity_deposit_funds_copy_0", com.nojom.client.R.layout.activity_deposit_funds_copy);
      sKeys.put("layout/activity_edit_paypal_0", com.nojom.client.R.layout.activity_edit_paypal);
      sKeys.put("layout/activity_email_verify_0", com.nojom.client.R.layout.activity_email_verify);
      sKeys.put("layout/activity_expert_filter_0", com.nojom.client.R.layout.activity_expert_filter);
      sKeys.put("layout/activity_expert_gig_filter_0", com.nojom.client.R.layout.activity_expert_gig_filter);
      sKeys.put("layout/activity_find_expert_0", com.nojom.client.R.layout.activity_find_expert);
      sKeys.put("layout/activity_freelancer_profile_0", com.nojom.client.R.layout.activity_freelancer_profile);
      sKeys.put("layout/activity_get_discount_0", com.nojom.client.R.layout.activity_get_discount);
      sKeys.put("layout/activity_gig_details_0", com.nojom.client.R.layout.activity_gig_details);
      sKeys.put("layout/activity_gig_extras_0", com.nojom.client.R.layout.activity_gig_extras);
      sKeys.put("layout/activity_hire_0", com.nojom.client.R.layout.activity_hire);
      sKeys.put("layout/activity_home_0", com.nojom.client.R.layout.activity_home);
      sKeys.put("layout/activity_how_it_works_0", com.nojom.client.R.layout.activity_how_it_works);
      sKeys.put("layout/activity_inf_service_0", com.nojom.client.R.layout.activity_inf_service);
      sKeys.put("layout/activity_infl_profile_all_0", com.nojom.client.R.layout.activity_infl_profile_all);
      sKeys.put("layout/activity_influencer_filter_0", com.nojom.client.R.layout.activity_influencer_filter);
      sKeys.put("layout/activity_influencer_profile_0", com.nojom.client.R.layout.activity_influencer_profile);
      sKeys.put("layout/activity_influencer_profile_copy_0", com.nojom.client.R.layout.activity_influencer_profile_copy);
      sKeys.put("layout/activity_job_post_title_0", com.nojom.client.R.layout.activity_job_post_title);
      sKeys.put("layout/activity_lawyer_home_0", com.nojom.client.R.layout.activity_lawyer_home);
      sKeys.put("layout/activity_lawyer_home_new_0", com.nojom.client.R.layout.activity_lawyer_home_new);
      sKeys.put("layout/activity_login_sign_up_0", com.nojom.client.R.layout.activity_login_sign_up);
      sKeys.put("layout/activity_main_0", com.nojom.client.R.layout.activity_main);
      sKeys.put("layout/activity_maintainance_0", com.nojom.client.R.layout.activity_maintainance);
      sKeys.put("layout/activity_milestone_0", com.nojom.client.R.layout.activity_milestone);
      sKeys.put("layout/activity_my_campaign_0", com.nojom.client.R.layout.activity_my_campaign);
      sKeys.put("layout/activity_my_invoices_0", com.nojom.client.R.layout.activity_my_invoices);
      sKeys.put("layout/activity_my_orders_0", com.nojom.client.R.layout.activity_my_orders);
      sKeys.put("layout/activity_my_profile_0", com.nojom.client.R.layout.activity_my_profile);
      sKeys.put("layout/activity_my_projects_0", com.nojom.client.R.layout.activity_my_projects);
      sKeys.put("layout/activity_new_policy_0", com.nojom.client.R.layout.activity_new_policy);
      sKeys.put("layout/activity_notification_0", com.nojom.client.R.layout.activity_notification);
      sKeys.put("layout/activity_offer_summary_0", com.nojom.client.R.layout.activity_offer_summary);
      sKeys.put("layout/activity_offer_title_0", com.nojom.client.R.layout.activity_offer_title);
      sKeys.put("layout/activity_order_details_0", com.nojom.client.R.layout.activity_order_details);
      sKeys.put("layout/activity_otp_0", com.nojom.client.R.layout.activity_otp);
      sKeys.put("layout/activity_otp_verify_0", com.nojom.client.R.layout.activity_otp_verify);
      sKeys.put("layout/activity_partner_about_0", com.nojom.client.R.layout.activity_partner_about);
      sKeys.put("layout/activity_partner_profile_0", com.nojom.client.R.layout.activity_partner_profile);
      sKeys.put("layout/activity_partner_with_us_0", com.nojom.client.R.layout.activity_partner_with_us);
      sKeys.put("layout/activity_payment_0", com.nojom.client.R.layout.activity_payment);
      sKeys.put("layout/activity_payment_new_0", com.nojom.client.R.layout.activity_payment_new);
      sKeys.put("layout/activity_phone_verify_0", com.nojom.client.R.layout.activity_phone_verify);
      sKeys.put("layout/activity_post_job_0", com.nojom.client.R.layout.activity_post_job);
      sKeys.put("layout/activity_post_job_new_0", com.nojom.client.R.layout.activity_post_job_new);
      sKeys.put("layout/activity_private_info_0", com.nojom.client.R.layout.activity_private_info);
      sKeys.put("layout/activity_profile_info_0", com.nojom.client.R.layout.activity_profile_info);
      sKeys.put("layout/activity_profile_stars_0", com.nojom.client.R.layout.activity_profile_stars);
      sKeys.put("layout/activity_project_details_0", com.nojom.client.R.layout.activity_project_details);
      sKeys.put("layout/activity_public_profile_0", com.nojom.client.R.layout.activity_public_profile);
      sKeys.put("layout/activity_search_tags_0", com.nojom.client.R.layout.activity_search_tags);
      sKeys.put("layout/activity_select_account_0", com.nojom.client.R.layout.activity_select_account);
      sKeys.put("layout/activity_select_expertise_0", com.nojom.client.R.layout.activity_select_expertise);
      sKeys.put("layout/activity_select_freelancer_0", com.nojom.client.R.layout.activity_select_freelancer);
      sKeys.put("layout/activity_sellers_service_0", com.nojom.client.R.layout.activity_sellers_service);
      sKeys.put("layout/activity_service_0", com.nojom.client.R.layout.activity_service);
      sKeys.put("layout/activity_service_sellers_search_0", com.nojom.client.R.layout.activity_service_sellers_search);
      sKeys.put("layout/activity_services_0", com.nojom.client.R.layout.activity_services);
      sKeys.put("layout/activity_share_discount_0", com.nojom.client.R.layout.activity_share_discount);
      sKeys.put("layout/activity_splash_0", com.nojom.client.R.layout.activity_splash);
      sKeys.put("layout/activity_update_location_0", com.nojom.client.R.layout.activity_update_location);
      sKeys.put("layout/activity_update_password_0", com.nojom.client.R.layout.activity_update_password);
      sKeys.put("layout/activity_username_0", com.nojom.client.R.layout.activity_username);
      sKeys.put("layout/activity_verification_0", com.nojom.client.R.layout.activity_verification);
      sKeys.put("layout/activity_verify_payment_0", com.nojom.client.R.layout.activity_verify_payment);
      sKeys.put("layout/activity_view_all_gig_0", com.nojom.client.R.layout.activity_view_all_gig);
      sKeys.put("layout/activity_wallet_0", com.nojom.client.R.layout.activity_wallet);
      sKeys.put("layout/activity_wallet_add_balance_0", com.nojom.client.R.layout.activity_wallet_add_balance);
      sKeys.put("layout/activity_web_view_0", com.nojom.client.R.layout.activity_web_view);
      sKeys.put("layout/activity_what_we_do_0", com.nojom.client.R.layout.activity_what_we_do);
      sKeys.put("layout/activity_withdraw_money_0", com.nojom.client.R.layout.activity_withdraw_money);
      sKeys.put("layout/chat_more_options_0", com.nojom.client.R.layout.chat_more_options);
      sKeys.put("layout/chip_0", com.nojom.client.R.layout.chip);
      sKeys.put("layout/content_addcard_0", com.nojom.client.R.layout.content_addcard);
      sKeys.put("layout/content_card_list_0", com.nojom.client.R.layout.content_card_list);
      sKeys.put("layout/custom_dialog_0", com.nojom.client.R.layout.custom_dialog);
      sKeys.put("layout/custom_partner_about_view_0", com.nojom.client.R.layout.custom_partner_about_view);
      sKeys.put("layout/custom_partner_textview_0", com.nojom.client.R.layout.custom_partner_textview);
      sKeys.put("layout/custom_toast_0", com.nojom.client.R.layout.custom_toast);
      sKeys.put("layout/dialog_add_contact_0", com.nojom.client.R.layout.dialog_add_contact);
      sKeys.put("layout/dialog_balance_deposit_0", com.nojom.client.R.layout.dialog_balance_deposit);
      sKeys.put("layout/dialog_bank_transfer_done_0", com.nojom.client.R.layout.dialog_bank_transfer_done);
      sKeys.put("layout/dialog_camera_document_select_0", com.nojom.client.R.layout.dialog_camera_document_select);
      sKeys.put("layout/dialog_cancel_freelancer_0", com.nojom.client.R.layout.dialog_cancel_freelancer);
      sKeys.put("layout/dialog_chat_now_0", com.nojom.client.R.layout.dialog_chat_now);
      sKeys.put("layout/dialog_chat_setting_0", com.nojom.client.R.layout.dialog_chat_setting);
      sKeys.put("layout/dialog_choose_platform_0", com.nojom.client.R.layout.dialog_choose_platform);
      sKeys.put("layout/dialog_close_project_0", com.nojom.client.R.layout.dialog_close_project);
      sKeys.put("layout/dialog_currency_0", com.nojom.client.R.layout.dialog_currency);
      sKeys.put("layout/dialog_custom_price_0", com.nojom.client.R.layout.dialog_custom_price);
      sKeys.put("layout/dialog_custom_price_option_0", com.nojom.client.R.layout.dialog_custom_price_option);
      sKeys.put("layout/dialog_delete_account_0", com.nojom.client.R.layout.dialog_delete_account);
      sKeys.put("layout/dialog_delete_project_0", com.nojom.client.R.layout.dialog_delete_project);
      sKeys.put("layout/dialog_deposit_0", com.nojom.client.R.layout.dialog_deposit);
      sKeys.put("layout/dialog_deposit_notes_0", com.nojom.client.R.layout.dialog_deposit_notes);
      sKeys.put("layout/dialog_email_verification_0", com.nojom.client.R.layout.dialog_email_verification);
      sKeys.put("layout/dialog_enter_cvv_0", com.nojom.client.R.layout.dialog_enter_cvv);
      sKeys.put("layout/dialog_feedback_0", com.nojom.client.R.layout.dialog_feedback);
      sKeys.put("layout/dialog_file_open_0", com.nojom.client.R.layout.dialog_file_open);
      sKeys.put("layout/dialog_file_option_menu_0", com.nojom.client.R.layout.dialog_file_option_menu);
      sKeys.put("layout/dialog_forgot_password_0", com.nojom.client.R.layout.dialog_forgot_password);
      sKeys.put("layout/dialog_free_trial_0", com.nojom.client.R.layout.dialog_free_trial);
      sKeys.put("layout/dialog_item_select_black_0", com.nojom.client.R.layout.dialog_item_select_black);
      sKeys.put("layout/dialog_language_0", com.nojom.client.R.layout.dialog_language);
      sKeys.put("layout/dialog_location_disclosure_0", com.nojom.client.R.layout.dialog_location_disclosure);
      sKeys.put("layout/dialog_location_skill_0", com.nojom.client.R.layout.dialog_location_skill);
      sKeys.put("layout/dialog_login_new_0", com.nojom.client.R.layout.dialog_login_new);
      sKeys.put("layout/dialog_login_sign_up_0", com.nojom.client.R.layout.dialog_login_sign_up);
      sKeys.put("layout/dialog_logout_0", com.nojom.client.R.layout.dialog_logout);
      sKeys.put("layout/dialog_my_profile_detail_0", com.nojom.client.R.layout.dialog_my_profile_detail);
      sKeys.put("layout/dialog_no_internet_0", com.nojom.client.R.layout.dialog_no_internet);
      sKeys.put("layout/dialog_open_website_0", com.nojom.client.R.layout.dialog_open_website);
      sKeys.put("layout/dialog_pay_done_0", com.nojom.client.R.layout.dialog_pay_done);
      sKeys.put("layout/dialog_payment_deposit_0", com.nojom.client.R.layout.dialog_payment_deposit);
      sKeys.put("layout/dialog_payment_deposit_done_0", com.nojom.client.R.layout.dialog_payment_deposit_done);
      sKeys.put("layout/dialog_payment_done_0", com.nojom.client.R.layout.dialog_payment_done);
      sKeys.put("layout/dialog_posting_done_0", com.nojom.client.R.layout.dialog_posting_done);
      sKeys.put("layout/dialog_promo_code_0", com.nojom.client.R.layout.dialog_promo_code);
      sKeys.put("layout/dialog_rate_app_0", com.nojom.client.R.layout.dialog_rate_app);
      sKeys.put("layout/dialog_rate_me_0", com.nojom.client.R.layout.dialog_rate_me);
      sKeys.put("layout/dialog_refund_payment_0", com.nojom.client.R.layout.dialog_refund_payment);
      sKeys.put("layout/dialog_refund_reason_0", com.nojom.client.R.layout.dialog_refund_reason);
      sKeys.put("layout/dialog_refund_user_0", com.nojom.client.R.layout.dialog_refund_user);
      sKeys.put("layout/dialog_release_payment_0", com.nojom.client.R.layout.dialog_release_payment);
      sKeys.put("layout/dialog_repost_delete_job_0", com.nojom.client.R.layout.dialog_repost_delete_job);
      sKeys.put("layout/dialog_security_code_0", com.nojom.client.R.layout.dialog_security_code);
      sKeys.put("layout/dialog_sort_by_filter_0", com.nojom.client.R.layout.dialog_sort_by_filter);
      sKeys.put("layout/dialog_stars_0", com.nojom.client.R.layout.dialog_stars);
      sKeys.put("layout/dialog_storage_disclosure_0", com.nojom.client.R.layout.dialog_storage_disclosure);
      sKeys.put("layout/dialog_timeline_0", com.nojom.client.R.layout.dialog_timeline);
      sKeys.put("layout/dialog_withdraw_0", com.nojom.client.R.layout.dialog_withdraw);
      sKeys.put("layout/dialog_zoom_portfolio_image_0", com.nojom.client.R.layout.dialog_zoom_portfolio_image);
      sKeys.put("layout/fragment_about_profile_0", com.nojom.client.R.layout.fragment_about_profile);
      sKeys.put("layout/fragment_all_popular_lawyer_0", com.nojom.client.R.layout.fragment_all_popular_lawyer);
      sKeys.put("layout/fragment_attachment_0", com.nojom.client.R.layout.fragment_attachment);
      sKeys.put("layout/fragment_balance_deposit_0", com.nojom.client.R.layout.fragment_balance_deposit);
      sKeys.put("layout/fragment_balance_history_0", com.nojom.client.R.layout.fragment_balance_history);
      sKeys.put("layout/fragment_balance_payment_0", com.nojom.client.R.layout.fragment_balance_payment);
      sKeys.put("layout/fragment_camp_detail_0", com.nojom.client.R.layout.fragment_camp_detail);
      sKeys.put("layout/fragment_camp_pay_0", com.nojom.client.R.layout.fragment_camp_pay);
      sKeys.put("layout/fragment_camp_stars_0", com.nojom.client.R.layout.fragment_camp_stars);
      sKeys.put("layout/fragment_chat_list_0", com.nojom.client.R.layout.fragment_chat_list);
      sKeys.put("layout/fragment_chat_manager_0", com.nojom.client.R.layout.fragment_chat_manager);
      sKeys.put("layout/fragment_choose_developer_0", com.nojom.client.R.layout.fragment_choose_developer);
      sKeys.put("layout/fragment_choose_skills_0", com.nojom.client.R.layout.fragment_choose_skills);
      sKeys.put("layout/fragment_deadline_0", com.nojom.client.R.layout.fragment_deadline);
      sKeys.put("layout/fragment_deposit_funds_0", com.nojom.client.R.layout.fragment_deposit_funds);
      sKeys.put("layout/fragment_describe_0", com.nojom.client.R.layout.fragment_describe);
      sKeys.put("layout/fragment_earn_money_0", com.nojom.client.R.layout.fragment_earn_money);
      sKeys.put("layout/fragment_enter_rate_0", com.nojom.client.R.layout.fragment_enter_rate);
      sKeys.put("layout/fragment_inf_agency_0", com.nojom.client.R.layout.fragment_inf_agency);
      sKeys.put("layout/fragment_inf_all_0", com.nojom.client.R.layout.fragment_inf_all);
      sKeys.put("layout/fragment_inf_services_0", com.nojom.client.R.layout.fragment_inf_services);
      sKeys.put("layout/fragment_lawyer_service_0", com.nojom.client.R.layout.fragment_lawyer_service);
      sKeys.put("layout/fragment_live_chat_0", com.nojom.client.R.layout.fragment_live_chat);
      sKeys.put("layout/fragment_my_level_0", com.nojom.client.R.layout.fragment_my_level);
      sKeys.put("layout/fragment_pay_type_0", com.nojom.client.R.layout.fragment_pay_type);
      sKeys.put("layout/fragment_post_job_0", com.nojom.client.R.layout.fragment_post_job);
      sKeys.put("layout/fragment_project_details_0", com.nojom.client.R.layout.fragment_project_details);
      sKeys.put("layout/fragment_project_payment_new_0", com.nojom.client.R.layout.fragment_project_payment_new);
      sKeys.put("layout/fragment_project_rate_0", com.nojom.client.R.layout.fragment_project_rate);
      sKeys.put("layout/fragment_project_status_0", com.nojom.client.R.layout.fragment_project_status);
      sKeys.put("layout/fragment_project_submit_0", com.nojom.client.R.layout.fragment_project_submit);
      sKeys.put("layout/fragment_projects_list_0", com.nojom.client.R.layout.fragment_projects_list);
      sKeys.put("layout/fragment_reviews_profile_0", com.nojom.client.R.layout.fragment_reviews_profile);
      sKeys.put("layout/fragment_select_rate_0", com.nojom.client.R.layout.fragment_select_rate);
      sKeys.put("layout/fragment_select_service_0", com.nojom.client.R.layout.fragment_select_service);
      sKeys.put("layout/fragment_skill_profile_0", com.nojom.client.R.layout.fragment_skill_profile);
      sKeys.put("layout/fragment_want_to_pay_0", com.nojom.client.R.layout.fragment_want_to_pay);
      sKeys.put("layout/fragment_win_0", com.nojom.client.R.layout.fragment_win);
      sKeys.put("layout/hire_grid_item_0", com.nojom.client.R.layout.hire_grid_item);
      sKeys.put("layout/home_grid_item_0", com.nojom.client.R.layout.home_grid_item);
      sKeys.put("layout/home_list_item_0", com.nojom.client.R.layout.home_list_item);
      sKeys.put("layout/home_page_item_0", com.nojom.client.R.layout.home_page_item);
      sKeys.put("layout/item_account_0", com.nojom.client.R.layout.item_account);
      sKeys.put("layout/item_banner_lawyer_0", com.nojom.client.R.layout.item_banner_lawyer);
      sKeys.put("layout/item_campaign_inprogress_0", com.nojom.client.R.layout.item_campaign_inprogress);
      sKeys.put("layout/item_campaign_stars_0", com.nojom.client.R.layout.item_campaign_stars);
      sKeys.put("layout/item_card_list_0", com.nojom.client.R.layout.item_card_list);
      sKeys.put("layout/item_card_view_0", com.nojom.client.R.layout.item_card_view);
      sKeys.put("layout/item_category_list_0", com.nojom.client.R.layout.item_category_list);
      sKeys.put("layout/item_chat_list_0", com.nojom.client.R.layout.item_chat_list);
      sKeys.put("layout/item_chat_messages_0", com.nojom.client.R.layout.item_chat_messages);
      sKeys.put("layout/item_chat_msg_0", com.nojom.client.R.layout.item_chat_msg);
      sKeys.put("layout/item_chip_view_0", com.nojom.client.R.layout.item_chip_view);
      sKeys.put("layout/item_choose_account_0", com.nojom.client.R.layout.item_choose_account);
      sKeys.put("layout/item_client_review_0", com.nojom.client.R.layout.item_client_review);
      sKeys.put("layout/item_custom_gig_details_0", com.nojom.client.R.layout.item_custom_gig_details);
      sKeys.put("layout/item_custom_gig_project_details_0", com.nojom.client.R.layout.item_custom_gig_project_details);
      sKeys.put("layout/item_custom_price_0", com.nojom.client.R.layout.item_custom_price);
      sKeys.put("layout/item_expert_list_0", com.nojom.client.R.layout.item_expert_list);
      sKeys.put("layout/item_expert_list_placeholder_0", com.nojom.client.R.layout.item_expert_list_placeholder);
      sKeys.put("layout/item_files_desc_0", com.nojom.client.R.layout.item_files_desc);
      sKeys.put("layout/item_filter_service_0", com.nojom.client.R.layout.item_filter_service);
      sKeys.put("layout/item_follwers_list_0", com.nojom.client.R.layout.item_follwers_list);
      sKeys.put("layout/item_gig_detail_pager_0", com.nojom.client.R.layout.item_gig_detail_pager);
      sKeys.put("layout/item_gig_details_0", com.nojom.client.R.layout.item_gig_details);
      sKeys.put("layout/item_gig_details_placeholder_0", com.nojom.client.R.layout.item_gig_details_placeholder);
      sKeys.put("layout/item_gig_extras_0", com.nojom.client.R.layout.item_gig_extras);
      sKeys.put("layout/item_gig_home_0", com.nojom.client.R.layout.item_gig_home);
      sKeys.put("layout/item_gig_home_new_0", com.nojom.client.R.layout.item_gig_home_new);
      sKeys.put("layout/item_gig_placeholder_0", com.nojom.client.R.layout.item_gig_placeholder);
      sKeys.put("layout/item_hire_0", com.nojom.client.R.layout.item_hire);
      sKeys.put("layout/item_home_0", com.nojom.client.R.layout.item_home);
      sKeys.put("layout/item_home_category_0", com.nojom.client.R.layout.item_home_category);
      sKeys.put("layout/item_home_category_lawyer_0", com.nojom.client.R.layout.item_home_category_lawyer);
      sKeys.put("layout/item_home_follower_0", com.nojom.client.R.layout.item_home_follower);
      sKeys.put("layout/item_home_is_top_0", com.nojom.client.R.layout.item_home_is_top);
      sKeys.put("layout/item_income_balane_0", com.nojom.client.R.layout.item_income_balane);
      sKeys.put("layout/item_inf_reviews_0", com.nojom.client.R.layout.item_inf_reviews);
      sKeys.put("layout/item_inf_service_0", com.nojom.client.R.layout.item_inf_service);
      sKeys.put("layout/item_inf_service_new_0", com.nojom.client.R.layout.item_inf_service_new);
      sKeys.put("layout/item_inf_services_0", com.nojom.client.R.layout.item_inf_services);
      sKeys.put("layout/item_inf_store_0", com.nojom.client.R.layout.item_inf_store);
      sKeys.put("layout/item_job_title_0", com.nojom.client.R.layout.item_job_title);
      sKeys.put("layout/item_language_agents_0", com.nojom.client.R.layout.item_language_agents);
      sKeys.put("layout/item_list_files_0", com.nojom.client.R.layout.item_list_files);
      sKeys.put("layout/item_list_files_survey_0", com.nojom.client.R.layout.item_list_files_survey);
      sKeys.put("layout/item_my_invoice_0", com.nojom.client.R.layout.item_my_invoice);
      sKeys.put("layout/item_my_store_0", com.nojom.client.R.layout.item_my_store);
      sKeys.put("layout/item_notification_0", com.nojom.client.R.layout.item_notification);
      sKeys.put("layout/item_notification_ph_0", com.nojom.client.R.layout.item_notification_ph);
      sKeys.put("layout/item_order_stars_0", com.nojom.client.R.layout.item_order_stars);
      sKeys.put("layout/item_orders_0", com.nojom.client.R.layout.item_orders);
      sKeys.put("layout/item_orders_ph_0", com.nojom.client.R.layout.item_orders_ph);
      sKeys.put("layout/item_orders_text_0", com.nojom.client.R.layout.item_orders_text);
      sKeys.put("layout/item_partner_answer_0", com.nojom.client.R.layout.item_partner_answer);
      sKeys.put("layout/item_partners_0", com.nojom.client.R.layout.item_partners);
      sKeys.put("layout/item_platform_0", com.nojom.client.R.layout.item_platform);
      sKeys.put("layout/item_platform_img_0", com.nojom.client.R.layout.item_platform_img);
      sKeys.put("layout/item_platform_selection_0", com.nojom.client.R.layout.item_platform_selection);
      sKeys.put("layout/item_policy_0", com.nojom.client.R.layout.item_policy);
      sKeys.put("layout/item_popular_0", com.nojom.client.R.layout.item_popular);
      sKeys.put("layout/item_popular_placeholder_0", com.nojom.client.R.layout.item_popular_placeholder);
      sKeys.put("layout/item_portfolio_list_0", com.nojom.client.R.layout.item_portfolio_list);
      sKeys.put("layout/item_portfolio_list_ph_0", com.nojom.client.R.layout.item_portfolio_list_ph);
      sKeys.put("layout/item_portfolio_small_0", com.nojom.client.R.layout.item_portfolio_small);
      sKeys.put("layout/item_profile_product_0", com.nojom.client.R.layout.item_profile_product);
      sKeys.put("layout/item_projects_list_0", com.nojom.client.R.layout.item_projects_list);
      sKeys.put("layout/item_proposal_freelancer_list_0", com.nojom.client.R.layout.item_proposal_freelancer_list);
      sKeys.put("layout/item_proposal_list_0", com.nojom.client.R.layout.item_proposal_list);
      sKeys.put("layout/item_referral_history_0", com.nojom.client.R.layout.item_referral_history);
      sKeys.put("layout/item_reviews_0", com.nojom.client.R.layout.item_reviews);
      sKeys.put("layout/item_reviews_ph_0", com.nojom.client.R.layout.item_reviews_ph);
      sKeys.put("layout/item_select_full_0", com.nojom.client.R.layout.item_select_full);
      sKeys.put("layout/item_select_language_0", com.nojom.client.R.layout.item_select_language);
      sKeys.put("layout/item_select_rate_0", com.nojom.client.R.layout.item_select_rate);
      sKeys.put("layout/item_select_service_0", com.nojom.client.R.layout.item_select_service);
      sKeys.put("layout/item_selected_stars_0", com.nojom.client.R.layout.item_selected_stars);
      sKeys.put("layout/item_service_0", com.nojom.client.R.layout.item_service);
      sKeys.put("layout/item_service_platform_0", com.nojom.client.R.layout.item_service_platform);
      sKeys.put("layout/item_service_sellers_0", com.nojom.client.R.layout.item_service_sellers);
      sKeys.put("layout/item_singlemultiselection_0", com.nojom.client.R.layout.item_singlemultiselection);
      sKeys.put("layout/item_skill_filter_0", com.nojom.client.R.layout.item_skill_filter);
      sKeys.put("layout/item_skills_0", com.nojom.client.R.layout.item_skills);
      sKeys.put("layout/item_skills_edit_0", com.nojom.client.R.layout.item_skills_edit);
      sKeys.put("layout/item_skills_post_0", com.nojom.client.R.layout.item_skills_post);
      sKeys.put("layout/item_sm_0", com.nojom.client.R.layout.item_sm);
      sKeys.put("layout/item_social_0", com.nojom.client.R.layout.item_social);
      sKeys.put("layout/item_social_gig_0", com.nojom.client.R.layout.item_social_gig);
      sKeys.put("layout/item_social_gig_details_0", com.nojom.client.R.layout.item_social_gig_details);
      sKeys.put("layout/item_social_inf_serv_0", com.nojom.client.R.layout.item_social_inf_serv);
      sKeys.put("layout/item_social_media_0", com.nojom.client.R.layout.item_social_media);
      sKeys.put("layout/item_social_media_profile_0", com.nojom.client.R.layout.item_social_media_profile);
      sKeys.put("layout/item_social_service_0", com.nojom.client.R.layout.item_social_service);
      sKeys.put("layout/item_stars_0", com.nojom.client.R.layout.item_stars);
      sKeys.put("layout/item_stars_platform_0", com.nojom.client.R.layout.item_stars_platform);
      sKeys.put("layout/item_submitted_files_0", com.nojom.client.R.layout.item_submitted_files);
      sKeys.put("layout/item_tag_view_0", com.nojom.client.R.layout.item_tag_view);
      sKeys.put("layout/item_uploaded_files_0", com.nojom.client.R.layout.item_uploaded_files);
      sKeys.put("layout/item_user_rehire_0", com.nojom.client.R.layout.item_user_rehire);
      sKeys.put("layout/item_verification_0", com.nojom.client.R.layout.item_verification);
      sKeys.put("layout/item_verified_with_0", com.nojom.client.R.layout.item_verified_with);
      sKeys.put("layout/item_view_all_category_0", com.nojom.client.R.layout.item_view_all_category);
      sKeys.put("layout/item_wallet_0", com.nojom.client.R.layout.item_wallet);
      sKeys.put("layout/item_workwith_0", com.nojom.client.R.layout.item_workwith);
      sKeys.put("layout/item_youtube_profile_0", com.nojom.client.R.layout.item_youtube_profile);
      sKeys.put("layout/lay_image_0", com.nojom.client.R.layout.lay_image);
      sKeys.put("layout/lay_text_0", com.nojom.client.R.layout.lay_text);
      sKeys.put("layout/layout_home_how_work_0", com.nojom.client.R.layout.layout_home_how_work);
      sKeys.put("layout/layout_milestone_payment_0", com.nojom.client.R.layout.layout_milestone_payment);
      sKeys.put("layout/layout_textview_0", com.nojom.client.R.layout.layout_textview);
      sKeys.put("layout/no_data_layout_0", com.nojom.client.R.layout.no_data_layout);
      sKeys.put("layout/timeline_item_0", com.nojom.client.R.layout.timeline_item);
      sKeys.put("layout/toolbar_back_0", com.nojom.client.R.layout.toolbar_back);
      sKeys.put("layout/toolbar_o_0", com.nojom.client.R.layout.toolbar_o);
      sKeys.put("layout/toolbar_progress_next_0", com.nojom.client.R.layout.toolbar_progress_next);
      sKeys.put("layout/toolbar_save_0", com.nojom.client.R.layout.toolbar_save);
      sKeys.put("layout/toolbar_title_0", com.nojom.client.R.layout.toolbar_title);
      sKeys.put("layout/view_agency_0", com.nojom.client.R.layout.view_agency);
      sKeys.put("layout/view_my_store_0", com.nojom.client.R.layout.view_my_store);
      sKeys.put("layout/view_overview_0", com.nojom.client.R.layout.view_overview);
      sKeys.put("layout/view_partner_0", com.nojom.client.R.layout.view_partner);
      sKeys.put("layout/view_portfolio_0", com.nojom.client.R.layout.view_portfolio);
      sKeys.put("layout/view_services_0", com.nojom.client.R.layout.view_services);
      sKeys.put("layout/view_social_media_0", com.nojom.client.R.layout.view_social_media);
      sKeys.put("layout/view_workwith_0", com.nojom.client.R.layout.view_workwith);
      sKeys.put("layout/view_youtube_0", com.nojom.client.R.layout.view_youtube);
      sKeys.put("layout/vw_layout_item_audio_pick_0", com.nojom.client.R.layout.vw_layout_item_audio_pick);
      sKeys.put("layout/vw_layout_item_folder_list_0", com.nojom.client.R.layout.vw_layout_item_folder_list);
    }
  }
}
