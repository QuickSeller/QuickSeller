package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.asac.quickseller.R;

public class PrivacyAndPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_and_policy);


        TextView privicyPolicy = (TextView)findViewById(R.id.privacypolicytextview);

        String htmlString = "<p><b>QuickSeller</b> is committed to ensuring the privacy and security of your personal information.</p>\n" +
                "\n" +
                "        <p>This Privacy Policy outlines how we collect, use, and safeguard information when you use our mobile application to buy or sell products.</p>\n" +
                "\n" +
                "        <b>Information We Collect</b>\n" +
                "\n" +
                "        <p><b>Personal Information:</b> When you register an account or make a purchase, we may collect personal information such as your name, email address, shipping address, phone number, and payment information.</p>\n" +
                "\n" +
                "        <p><b>Usage Information:</b> We may collect information about your interactions with our application, including pages visited, products viewed, and actions taken within the app.</p>\n" +
                "\n" +
                "        <b>How We Use Your Information</b>\n" +
                "\n" +
                "        <p><b>To Provide Services:</b> We use the collected information to process your orders, provide customer support, and personalize your experience.</p>\n" +
                "\n" +
                "        <p><b>Communication:</b> We may use your contact information to send you updates about your orders, promotional offers, or important announcements related to our services.</p>\n" +
                "\n" +
                "        <p><b>Improvement and Analysis:</b> We analyze user behaviors and preferences to improve our products, services, and marketing strategies.</p>\n" +
                "\n" +
                "        <b>Data Security</b>\n" +
                "\n" +
                "        <p>We take stringent measures to protect your information from unauthorized access, alteration, disclosure, or destruction. We use encryption, secure servers, and other industry-standard security practices to safeguard your data.</p>\n" +
                "\n" +
                "        <b>Third-Party Services</b>\n" +
                "\n" +
                "        <p>We may use third-party services such as payment gateways or analytics tools. These services have their own privacy policies, and we encourage you to review them for understanding how they handle your information.</p>\n" +
                "\n" +
                "        <b>Your Choices</b>\n" +
                "\n" +
                "        <p>You can update or delete your account information at any time. Additionally, you can opt-out of receiving promotional communications.</p>\n" +
                "\n" +
                "        <b>Children's Privacy</b>\n" +
                "\n" +
                "        <p>Our services are not directed to individuals under the age of 13. We do not knowingly collect personal information from children. If you are a parent or guardian and believe that your child has provided us with personal information, please contact us, and we will take steps to remove that information.</p>\n" +
                "\n" +
                "        <b>Changes to this Privacy Policy</b>\n" +
                "\n" +
                "        <p>We reserve the right to update our Privacy Policy. Any modifications will be effective upon posting the updated policy on our website or app. By continuing to use our services after changes are made, you agree to the updated policy.</p>\n" +
                "\n" +
                "        <b>Contact Us</b>\n" +
                "\n" +
                "        <p>If you have any questions or concerns regarding this Privacy Policy, please contact us at <a href=\"mailto:info@QuickSeller.com\">info@QuickSeller.com</a>.</p>\n";


        CharSequence styledText = Html.fromHtml(htmlString);


        privicyPolicy.setText(styledText);


    }



}