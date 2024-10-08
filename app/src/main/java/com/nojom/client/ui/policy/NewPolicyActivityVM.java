package com.nojom.client.ui.policy;

import android.annotation.SuppressLint;
import android.app.Application;
import android.text.Html;

import androidx.lifecycle.AndroidViewModel;

import com.nojom.client.R;
import com.nojom.client.databinding.ActivityNewPolicyBinding;
import com.nojom.client.ui.BaseActivity;

class NewPolicyActivityVM extends AndroidViewModel {
    private ActivityNewPolicyBinding binding;
    @SuppressLint("StaticFieldLeak")
    private BaseActivity activity;

    NewPolicyActivityVM(Application application, ActivityNewPolicyBinding newPolicyBinding, BaseActivity newPolicyActivity) {
        super(application);
        binding = newPolicyBinding;
        activity = newPolicyActivity;
        initData();
    }

    private void initData() {
        binding.toolbar.tvTitle.setText(activity.getString(R.string.privacy_policy));
        String text = "<p>CLIENTS APP or Website:</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Overview:</p>\n" +
                "<p>By accessing our App/website, you agree with the following terms with InfluencerBird:</p>\n" +
                "<p>&nbsp;- The App/website is a freelancing app where users collaborate and apply for online services. Customers (Users) should sign up on the App in order to avail the service. The platform enables Users to work together online to complete and pay for Projects/jobs.</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>We may, from time to time, and without notice, change or add to the App/Website or the information and services described in it.&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>TERMS AND CONDITIONS:</p>\n" +
                "<p>You agree that the company has the right to view all the conversations done through the app/website for investigation and verification purposes.</p>\n" +
                "<p>You agree to provide valid and correct personal information or documents during signing up or account creation.</p>\n" +
                "<p>You agree to cooperate by sending valid and correct evidence or proofs during investigation when filing any complaint.</p>\n" +
                "<p>You agree NOT to post any personal contact details during posting of job and conversation with the star.</p>\n" +
                "<p>You agree that the company can make a full decision on the disputes.</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>In availing the service, you agreed NOT TO DO any of the following:</p>\n" +
                "<p>fail to deliver payment for services delivered to you.</p>\n" +
                "<p>Share personal contact details or manipulate the star to send any amount to your account or send the completed job/output directly on your personal account.</p>\n" +
                "<p>post false, inaccurate, misleading, deceptive, defamatory or offensive content (including personal information).</p>\n" +
                "<p>post spam, unsolicited, or bulk electronic communications, chain letters, or pyramid schemes</p>\n" +
                "<p>Post false or invalid contact information as your identity.</p>\n" +
                "<p>Create multiple accounts by using fake information.</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>REVISION POLICY</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Revision requests are entertained within 48 hours (or 2 days ONLY) of order submission. Any change in the original instructions will be considered as a new order or rewrite (depending upon the changes) and will be charged according to the stars pricing.</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Important Note:</p>\n" +
                "<p>If you are not satisfied with the output, you may place a request for Free Revision.(within 48 hours only)</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>REFUND POLICY</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Refunds can be requested at any time for any funds that you have paid into your account except if service has already been completed for 15 days or the file sent by the star was already downloaded (on the clients end). If the output is incorrect/erroneous, it should be reported within 48 hours ONLY.</p>\n" +
                "<p>*Always check before releasing the payment.</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Refund process takes within 5-10 business days. Once the payment was released, a refund request is only acceptable within 15 days.&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>Refund request will be entertained IF:</p>\n" +
                "<p>You can provide us with strong reasons to support your claims (includes a screenshot of conversation with the star or any proof that causes delay or failure of submission on time, that star did not follow the given instruction etc.).</p>\n" +
                "<p>(Please note that once you demand a refund, the paper will be published online so you can no longer use it as it will appear as copyright )</p>\n" +
                "<p>The paper was not submitted during the deadline (without any prior deadline extension request directly from the client or from the Support).</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>FULL AMOUNT REFUND:</p>\n" +
                "<p>Only if:</p>\n" +
                "<p>Service was canceled BEFORE the star started to work on the job. (star, Support and the customer must be informed immediately)</p>\n" +
                "<p>When the star failed to complete the job during the deadline.</p>\n" +
                "<p>When both parties did not agree on the deposit amount that has been paid.</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>IDENTITY/ KNOW YOUR CUSTOMER</p>\n" +
                "<p>Upon signing up, you authorise us, directly or through third parties, to make any inquiries we consider necessary to validate your identity.</p>\n" +
                "<p>Requirement:</p>\n" +
                "<p>(1) Provide complete and valid information to us, which may include your complete name or other information that will allow us to reasonably identify you.</p>\n" +
                "<p>(2) Take steps to confirm ownership of your email address.</p>\n" +
                "<p>(3) Verify your information against third party databases or through other sources.</p>\n" +
                "<p>You must also, at our request, provide copies of identification documents (such as your passport or driver's licence).</p>\n" +
                "<p>We reserve the right to close, suspend, or limit access to your Account, the Mobile App, Website and/or star Services in the event we are unable to obtain or verify to our satisfaction the information which we request under this section.</p>\n" +
                "<p>We reserve the right to update your particulars on the mobile app/website in order to match any identity documentation that has been provided. If you are not fully verified you may not be able to withdraw funds from your Account, and other restrictions may apply.</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>";
        binding.text.setText(Html.fromHtml(text));
        binding.toolbar.imgBack.setOnClickListener(v -> activity.onBackPressed());
    }
}
