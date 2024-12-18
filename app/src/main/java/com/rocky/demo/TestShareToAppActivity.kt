package com.rocky.demo

import android.content.Context
import android.content.Intent
import android.graphics.ColorSpace.match
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rocky.demo.databinding.ActivityTestShareToAppBinding
import java.util.regex.Matcher
import java.util.regex.Pattern

class TestShareToAppActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityTestShareToAppBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btText1.setOnClickListener {
            testREMatch()
        }
        binding.btShare.setOnClickListener {
//            testShareLinkToVDM(this, binding.etUrl.text.toString())

//            testShareLinkToVDM(this, "https://youtube.com/shorts/XV79i3aeKWc?si=aFhROe9IHgvIbnLr")

            testShareLinkToVDM(this, "http://music.youtube.com/")
//            testShareLinkToVDM(this, "https://music.youtube.com/watch?v=JEvQROCyvaM&si=FEUYlwqD9nLJR55Y")
//            testShareLinkToVDM(this, "https://music.youtube.com/playlist?list=RDCLAK5uy_lnrm39tngswIEjpm8d7-is3ajsYC-D_EU&playnext=1&si=3NrjRt2GiZ0SS1gz")
//            testShareLinkToVDM(this, "https://music.youtube.com/channel/UCRw0x9_EfawqmgDI2IgQLLg?si=-58MsjP_grbyUrpO")

//            testShareLinkToVDM(this, "https://youtube.com/playlist?list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp")
//            testShareLinkToVDM(this, "https://youtube.com/@ytspread?si=A2XQjErXJieohxqN")
//            testShareLinkToVDM(this, "https://www.youtube.com/watch?v=kW27yAhxjDo")
//            testShareLinkToVDM(this, "https://m.youtube.com/watch?v=kW27yAhxjDo")
//            testShareLinkToVDM(this, "https://m.youtube.com/youtubei/v1/player?v=kW27yAhxjDo")
//            testShareLinkToVDM(this, "https://youtu.be/kW27yAhxjDo?si=tj63AjRnuO4LOjOn")
//            testShareLinkToVDM(this, "https://youtube.com/watch?v=kW27yAhxjDo")
//            testShareLinkToVDM(this, "https://m.youtube.com/live/lvbdhV87EDY?si=ict3igD3otI4_dqT#")
//            testShareLinkToVDM(this, "https://www.youtube.com/live/lvbdhV87EDY?si=ict3igD3otI4_dqT#")
//            testShareLinkToVDM(this, "https://youtu.be/live/lvbdhV87EDY?si=ict3igD3otI4_dqT#")
//            testShareLinkToVDM(this, "https://youtube.com/live/lvbdhV87EDY?si=ict3igD3otI4_dqT#")
        }
    }

    private fun testShorts() {
        val regexList = listOf(
            "^https?://www\\.youtube\\.com/shorts/([^/]*?)(?:\\?.*)?$",
            "^https?://m\\.youtube\\.com/shorts/([^/]*?)(?:\\?.*)?$",
            "^https?://youtube\\.com/shorts/([^/]*?)(?:\\?.*)?$"
        )
        match(regexList, "https://youtube.com/shorts/glhWLHuhYWs?si=d2I0uPS_YTZKChs2", true)
    }
    private fun testMusicHome() {
        val regexList = listOf(
            "^https?://music\\.youtube\\.com/?(?:\\?.*)?$"
        )
        match(regexList, "http://music.youtube.com/", true)
        match(regexList, "https://music.youtube.com/", true)
        match(regexList, "http://music.youtube.com", true)
        match(regexList, "https://music.youtube.com", true)
        match(regexList, "https://music.youtube.com/?source=gpm", true)
        match(regexList, "https://music.youtube.com?source=gpm", true)
        match(regexList, "https://music.youtube.com/watch", false)
        match(regexList, "https://music.youtube.com/watch?source=gpm", false)
    }
    private fun testMusicDetail() {
        val regexList = listOf(
            "^https?://music\\.youtube\\.com/watch\\?(?:.*?&)?v=(.*?)(?:&.*)?$"
        )
        match(regexList, "https://music.youtube.com/watch?v=JEvQROCyvaM", true)
        match(regexList, "https://music.youtube.com/watch?v=JEvQROCyvaM&si=FEUYlwqD9nLJR55Y", true)
        match(regexList, "https://music.youtube.com/watch?xx=yy&v=JEvQROCyvaM&si=FEUYlwqD9nLJR55Y", true)
        match(regexList, "https://music.youtube.com/watchxxx?v=JEvQROCyvaM&si=FEUYlwqD9nLJR55Y", false)
        match(regexList, "https://music.youtube.com/watch/xxx?v=JEvQROCyvaM&si=FEUYlwqD9nLJR55Y", false)
    }
    private fun testMusicPlaylist() {
        val regexList = listOf(
            "^https?://music\\.youtube\\.com/playlist\\?(?:.*?&)?list=(.*?)(?:&.*)?$"
        )
        match(regexList, "https://music.youtube.com/playlist?list=RDCLAK5uy_lnrm39tngswIEjpm8d7-is3ajsYC-D_EU", true)
        match(regexList, "https://music.youtube.com/playlist?list=RDCLAK5uy_lnrm39tngswIEjpm8d7-is3ajsYC-D_EU&playnext=1&si=3NrjRt2GiZ0SS1gz", true)
        match(regexList, "https://music.youtube.com/playlist?xx=yy&list=RDCLAK5uy_lnrm39tngswIEjpm8d7-is3ajsYC-D_EU&playnext=1&si=3NrjRt2GiZ0SS1gz", true)
        match(regexList, "https://music.youtube.com/playlistxxx?list=RDCLAK5uy_lnrm39tngswIEjpm8d7-is3ajsYC-D_EU&playnext=1&si=3NrjRt2GiZ0SS1gz", false)
        match(regexList, "https://music.youtube.com/playlist/xxx?list=RDCLAK5uy_lnrm39tngswIEjpm8d7-is3ajsYC-D_EU&playnext=1&si=3NrjRt2GiZ0SS1gz", false)
    }
    private fun testMusicChannel() {
        val regexList = listOf(
            "^https?://music\\.youtube\\.com/channel/([^/]*?)(?:\\?.*)?$"
        )
        match(regexList, "https://music.youtube.com/channel/UCRw0x9_EfawqmgDI2IgQLLg?si=-58MsjP_grbyUrpO", true)
        match(regexList, "https://music.youtube.com/channel/UCRw0x9_EfawqmgDI2IgQLLg?", true)
        match(regexList, "https://music.youtube.com/channel/UCRw0x9_EfawqmgDI2IgQLLg", true)
    }
    private fun testVideoPlaylist() {
        val regexList = listOf(
            "^https?://www\\.youtube\\.com/playlist\\?(?:.*?&)?list=(.*?)(?:&.*)?$",
            "^https?://m\\.youtube\\.com/playlist\\?(?:.*?&)?list=(.*?)(?:&.*)?$",
            "^https?://youtube\\.com/playlist\\?(?:.*?&)?list=(.*?)(?:&.*)?$"
        )
        match(regexList, "https://www.youtube.com/playlist?list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka", true)
        match(regexList, "https://www.youtube.com/playlist?list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", true)
        match(regexList, "https://www.youtube.com/playlist?xx=yy&list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", true)
        match(regexList, "https://www.youtube.com/playlistxx?xx=yy&list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", false)
        match(regexList, "https://www.youtube.com/playlist/xx?xx=yy&list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", false)
        match(regexList, "https://m.youtube.com/playlist?list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka", true)
        match(regexList, "https://m.youtube.com/playlist?list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", true)
        match(regexList, "https://m.youtube.com/playlist?xx=yy&list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", true)
        match(regexList, "https://m.youtube.com/playlistxx?xx=yy&list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", false)
        match(regexList, "https://m.youtube.com/playlist/xx?xx=yy&list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", false)
        match(regexList, "https://youtube.com/playlist?list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka", true)
        match(regexList, "https://youtube.com/playlist?list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", true)
        match(regexList, "https://youtube.com/playlist?xx=yy&list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", true)
        match(regexList, "https://youtube.com/playlistxx?xx=yy&list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", false)
        match(regexList, "https://youtube.com/playlist/xx?xx=yy&list=PLX4Bvko-3uFzD_6hrL6YvxtLHzhrygjka&si=mAqMyRdAKN4tRPEp", false)
    }
    private fun testVideoChannel() {
        val regexList = listOf(
            "^https?://www\\.youtube\\.com/@([^/]*?)(?:\\?.*)?$",
            "^https?://www\\.youtube\\.com/channel/([^/]*?)(?:\\?.*)?$",
            "^https?://www\\.youtube\\.com/c/([^/]*?)(?:\\?.*)?$",
            "^https?://www\\.youtube\\.com/user/([^/]*?)(?:\\?.*)?$",
            "^https?://m\\.youtube\\.com/@([^/]*?)(?:\\?.*)?$",
            "^https?://m\\.youtube\\.com/channel/([^/]*?)(?:\\?.*)?$",
            "^https?://m\\.youtube\\.com/c/([^/]*?)(?:\\?.*)?$",
            "^https?://m\\.youtube\\.com/user/([^/]*?)(?:\\?.*)?$",
            "^https?://youtube\\.com/@([^/]*?)(?:\\?.*)?$",
            "^https?://youtube\\.com/channel/([^/]*?)(?:\\?.*)?$",
            "^https?://youtube\\.com/c/([^/]*?)(?:\\?.*)?$",
            "^https?://youtube\\.com/user/([^/]*?)(?:\\?.*)?$"
        )
        match(regexList, "https://www.youtube.com/@ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://www.youtube.com/channel/ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://www.youtube.com/c/ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://www.youtube.com/user/ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://m.youtube.com/@ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://m.youtube.com/channel/ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://m.youtube.com/c/ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://m.youtube.com/user/ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://youtube.com/@ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://youtube.com/channel/ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://youtube.com/c/ytspread?si=A2XQjErXJieohxqN", true)
        match(regexList, "https://youtube.com/user/ytspread?si=A2XQjErXJieohxqN", true)
    }
    private fun testVideoDetail() {
        val regexList = listOf(
            "^https?://www\\.youtube\\.com/watch\\?(?:.*?&)?v=(.*?)(?:&.*)?$",
            "^https?://m\\.youtube\\.com/watch\\?(?:.*?&)?v=(.*?)(?:&.*)?$",
            "^https?://m\\.youtube\\.com/youtubei/v1/player\\?(?:.*?&)?v=(.*?)(?:&.*)?$",
            "^https?://youtube\\.com/watch\\?(?:.*?&)?v=(.*?)(?:&.*)?$",
            "^https?://youtu\\.be/([^/]*?)(?:\\?.*)?$",
            "^https?://www\\.youtube\\.com/live/([^/]*?)(?:\\?.*)?$",
            "^https?://m\\.youtube\\.com/live/([^/]*?)(?:\\?.*)?$",
            "^https?://youtube\\.com/live/([^/]*?)(?:\\?.*)?$",
            "^https?://youtu\\.be/live/([^/]*?)(?:\\?.*)?$"
        )

        match(regexList, "https://www.youtube.com/watch?v=kW27yAhxjDo", true)
        match(regexList, "https://m.youtube.com/watch?v=kW27yAhxjDo", true)
        match(regexList, "https://m.youtube.com/youtubei/v1/player?v=kW27yAhxjDo", true)
        match(regexList, "https://youtube.com/watch?v=kW27yAhxjDo", true)
        match(regexList, "https://youtu.be/kW27yAhxjDo?si=tj63AjRnuO4LOjOn", true)
        match(regexList, "https://youtu.be/kW27yAhxjDo?", true)
        match(regexList, "https://youtu.be/kW27yAhxjDo", true)

        match(regexList, "https://www.youtube.com/live/lvbdhV87EDY?si=ict3igD3otI4_dqT#", true)
        match(regexList, "https://www.youtube.com/live/lvbdhV87EDY?", true)
        match(regexList, "https://www.youtube.com/live/lvbdhV87EDY", true)
        match(regexList, "https://m.youtube.com/live/lvbdhV87EDY?si=ict3igD3otI4_dqT#", true)
        match(regexList, "https://youtube.com/live/lvbdhV87EDY?si=ict3igD3otI4_dqT#", true)
        match(regexList, "https://youtu.be/live/lvbdhV87EDY?si=ict3igD3otI4_dqT#", true)
        match(regexList, "https://youtu.be/live/lvbdhV87EDY?", true)
        match(regexList, "https://youtu.be/live/lvbdhV87EDY", true)
    }

    private fun testREMatch() {

        testShorts()
        testMusicHome()
        testMusicDetail()
        testMusicPlaylist()
        testMusicChannel()
        testVideoPlaylist()
        testVideoChannel()
        testVideoDetail()

    }

    private fun match(regexList: List<String>, input: String, expected: Boolean) {
        val any = regexList.any { regex ->
            val pattern: Pattern = Pattern.compile(regex)
            val matcher: Matcher = pattern.matcher(input)
            if (matcher.matches()) {
                val groupCount = matcher.groupCount()
                Log.d(
                    "testdemo",
                    "group1=${if(groupCount>=1) matcher.group(1) else null}, groupCount=$groupCount, input: $input, regex: $regex"
                )
                true
            } else {
                false
            }
        }
        val resultOK = (any == expected)
        if (resultOK) {
            Log.i(
                "testdemo",
                "result: pass, match_result: $any, expected_result: $expected, input: $input"
            )
        } else {
            Log.e(
                "testdemo",
                "result: fail, match_result: $any, expected_result: $expected, input: $input"
            )
        }
    }

    private fun testShareLinkToVDM(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_SEND).apply {
            this.setPackage("com.android.video.browser")
            this.type = "text/plain"
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.putExtra(Intent.EXTRA_TEXT, url)
        })
    }
}