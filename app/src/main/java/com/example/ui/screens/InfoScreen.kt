package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Amber400
import com.example.ui.theme.Emerald400
import com.example.ui.theme.GymBorder
import com.example.ui.theme.Sky400
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun InfoScreen(
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // App Architecture Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFF0F172A), RoundedCornerShape(18.dp))
          .border(1.dp, Color(0xFF334155), RoundedCornerShape(18.dp))
          .padding(18.dp)
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .background(Color(0x2210B981), RoundedCornerShape(12.dp))
                .border(0.8.dp, Color(0x5510B981), RoundedCornerShape(12.dp)),
              contentAlignment = Alignment.Center
            ) {
              Text(text = "💪", fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
              Text(
                text = "GYM Dost Architecture",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = "Zero Cloud Database Cost & Offline Friendly",
                fontSize = 11.sp,
                color = TextSecondary
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "GYM Dost complete Client-Side Local Storage Architecture par chalta hai. Iska matlab hai ki aapka daily exercise status, diet plans, profile metrics aur intake logs phone ki internal memory me automatically persist hote hain. Iske liye koi paid cloud server ya monthly database charges nahi dene padenge.",
            fontSize = 12.sp,
            color = Color(0xFFCBD5E1),
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(16.dp))

          // 3 Feature Boxes
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            ArchitectureFeature(
              title = "Zero Database Cost",
              titleColor = Emerald400,
              desc = "Complete on-device offline storage used. No external server subscription needed."
            )
            ArchitectureFeature(
              title = "Real-time Remaining Macros",
              titleColor = Sky400,
              desc = "Automatic subtraction: jaise hi kuch khayein, remaining calories aur protein auto-calculate ho jate hain."
            )
            ArchitectureFeature(
              title = "Sunday Off & Timeline",
              titleColor = Amber400,
              desc = "Weekly Sunday recovery day with customized 3-month, 6-month ya 1-year transformation plan."
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Made in Kanpur & Support Card
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(Color(0xFF030712), RoundedCornerShape(12.dp))
              .border(1.dp, Color(0xFF334155), RoundedCornerShape(12.dp))
              .padding(14.dp)
          ) {
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🇮🇳", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Made with ❤️ in Kanpur",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextPrimary
                )
              }

              Spacer(modifier = Modifier.height(4.dp))

              Text(
                text = "Aapka local personal gym & diet companion.",
                fontSize = 11.sp,
                color = TextSecondary
              )

              Spacer(modifier = Modifier.height(8.dp))

              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "Official Support: ",
                  fontSize = 11.sp,
                  color = TextSecondary
                )
                Text(
                  text = "aadiroide@gmail.com",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = Emerald400,
                  textDecoration = TextDecoration.Underline,
                  modifier = Modifier
                    .clickable {
                      val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:aadiroide@gmail.com")
                        putExtra(Intent.EXTRA_SUBJECT, "GYM Dost Feedback & Support")
                      }
                      context.startActivity(Intent.createChooser(intent, "Send Email"))
                    }
                    .testTag("link_support_email")
                )
              }
            }
          }
        }
      }
    }

    // AdMob Banner Slot (Adaptive Banner Mock for APK display)
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0x221E293B), RoundedCornerShape(14.dp))
          .border(1.dp, GymBorder, RoundedCornerShape(14.dp))
          .padding(12.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(
            text = "GOOGLE ADMOB BANNER SLOT (320x50)",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = TextMuted,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
              .background(Color(0xFF030712), RoundedCornerShape(8.dp))
              .border(1.dp, Color(0x33334155), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "📢 AdMob Banner Space (Clean & Non-Intrusive)",
              fontSize = 11.sp,
              color = TextSecondary
            )
          }
        }
      }
    }

    // Footer
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "© 2026 GYM Dost • 100% Client-Side Privacy Protected",
          fontSize = 10.sp,
          color = TextMuted
        )
      }
    }
  }
}

@Composable
private fun ArchitectureFeature(
  title: String,
  titleColor: Color,
  desc: String
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color(0xFF030712), RoundedCornerShape(10.dp))
      .border(1.dp, Color(0xFF334155), RoundedCornerShape(10.dp))
      .padding(12.dp)
  ) {
    Column {
      Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = titleColor)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = desc, fontSize = 10.sp, color = TextSecondary, lineHeight = 14.sp)
    }
  }
}
