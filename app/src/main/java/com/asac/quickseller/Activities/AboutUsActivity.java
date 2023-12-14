package com.asac.quickseller.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.asac.quickseller.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        TextView aboutUs = (TextView)findViewById(R.id.aboutustextview);

        String htmlString = "<html><body>" +
                "<div style='text-align: center;'>" +
                "<h1 style='font-size: 24px;'>QuickSeller</h1>" +
                "<p style='font-size: 16px;'>Revolutionize your buying and selling experience with QuickSeller! Our seamless mobile application enables effortless trading of both new and used items.</p>" +
                "<h2 style='font-size: 20px;'>Why Choose QuickSeller?</h2>" +
                "<ul style='list-style-type: disc;'>" +
                "<li>User-Friendly Interface: Navigate with ease and convenience.</li>" +
                "<li>Top-notch Security: Trustworthy transactions in a secure environment.</li>" +
                "<li>Streamlined Process: Enjoy hassle-free buying and selling.</li>" +
                "</ul>" +
                "<p>Join QuickSeller today and discover a new level of convenience in your transactions!</p>" +
                "<button style='padding: 10px 20px; background-color: #4285F4; color: white; border: none; border-radius: 5px;'>Sign Up / Get Started</button>" +
                "</div></body></html>";

        CharSequence styledText = Html.fromHtml(htmlString);


        aboutUs.setText(styledText);
    }
}