//package com.fosung.lighthouse.fosunglibs1;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.blankj.utilcode.util.AppUtils;
//import com.blankj.utilcode.util.Utils;
//import com.gelitenight.waveview.library.WaveView;
//
////import com.app.hubert.guide.newbieguide.FirstActivity;
////import com.blankj.utilcode.util.Utils;
////import com.example.slbyanzheng.ZhiwenActtivity;
////import com.geek.libnsfw.NsfwAct;
////import com.geek.libocr.ScanAct1;
////import com.geek.libpicturecompressor.PictureCompressorActivity;
////import com.geek.libshadowlayout.ShadowMainActivity;
////import com.vincent.videocompressor.activity.VideoComPressorActivity;
//
///**
// * @author houjie
// * @date 2022/4/13
// */
//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    private Button button, button1, button2, button3, button4, button5, button6, button7, button8, button9,
//            button10, button11, button12, button13, button14, button15, button16;
//
//    private TextView tv1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        //
//        Utils.init(App11.get());// com.blankj:utilcode:1.17.3
//        button = findViewById(R.id.button);
//        button1 = findViewById(R.id.button1);
//        button2 = findViewById(R.id.button2);
//        button3 = findViewById(R.id.button3);
//        button4 = findViewById(R.id.button4);
//        button5 = findViewById(R.id.button5);
//        button6 = findViewById(R.id.button6);
//        button7 = findViewById(R.id.button7);
//        button8 = findViewById(R.id.button8);
//        button9 = findViewById(R.id.button9);
//        button10 = findViewById(R.id.button10);
//        button11 = findViewById(R.id.button11);
//        button12 = findViewById(R.id.button12);
//        button13 = findViewById(R.id.button13);
//        button14 = findViewById(R.id.button14);
//        button15 = findViewById(R.id.button15);
//        button16 = findViewById(R.id.button16);
//        button.setOnClickListener(this);
//        button1.setOnClickListener(this);
//        button2.setOnClickListener(this);
//        button3.setOnClickListener(this);
//        button4.setOnClickListener(this);
//        button5.setOnClickListener(this);
//        button6.setOnClickListener(this);
//        button7.setOnClickListener(this);
//        button8.setOnClickListener(this);
//        button9.setOnClickListener(this);
//        button10.setOnClickListener(this);
//        button11.setOnClickListener(this);
//        button12.setOnClickListener(this);
//        button13.setOnClickListener(this);
//        button14.setOnClickListener(this);
//        button15.setOnClickListener(this);
//        button16.setOnClickListener(this);
////        new PgyerSDKManager.Init()
////                .setContext(getApplicationContext()) //?????????????????????
////                .start();
////        startActivity(new Intent(MainActivity.this, ScanAct2.class));
////        String strBase64 = Base64.encodeToString("??????".getBytes(), Base64.DEFAULT);// ??????
//////        tv1.setText(EncodeUtils.base64Encode2String("??????".getBytes()));
////        String str2 = new String(Base64.decode(strBase64.getBytes(), Base64.DEFAULT));// ??????
////        tv1.setText(strBase64 + "," + str2);
//        WaveView waveView;
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.button:
//                /*????????????*/
//                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.NsfwAct");
////                Intent intent = new Intent(this, NsfwAct.class);
//                startActivity(intent);
//                //
////                String reviseBpmnFile = "http://cdn2.cdn.haier-jiuzhidao.com/tensorflowso/version.xml";
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        String fileString = "";
////                        try {
////                            //??????????????????URl
////                            InputStream in = LoadFile.downLoadFile(reviseBpmnFile);
////                            //
//////                            List<XmlBean> mlist = LoadFile.getNodeList(in);
////                            List<XmlBean> mlist = DOMService.getPersons(in);
////
////                            //?????? xml ??????
//////                            File fileinput = LoadFile.copyInputStreamToFile(in);
//////
//////                            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//////                            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//////                            Document doc = dBuilder.parse(fileinput);
//////                            //???xml???????????????String
//////                            StringWriter sw = new StringWriter();
//////                            TransformerFactory tf = TransformerFactory.newInstance();
//////                            Transformer transformer = tf.newTransformer();
//////                            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
//////                            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//////                            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//////                            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//////                            transformer.transform(new DOMSource(doc), new StreamResult(sw));
//////                            fileString = sw.toString();
////
////                        } catch (Exception e) {
////                            LogUtils.e("getReviseBpmnFile ?????????????????????url???" + reviseBpmnFile);
////                            try {
////                                throw new Exception("?????????????????????");
////                            } catch (Exception ex) {
////                                ex.printStackTrace();
////                            }
////                        }
////                        //
////                        String pg_name = AppUtils.getAppPackageName();
////
////
////                    }
////                }).start();
//                break;
//            case R.id.button1:
//                /*ocr??????*/
//                Intent intent1 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ScanAct1");
//                startActivity(intent1);
//                break;
//            case R.id.button2:
//                /*???????????????*/
//                Intent intent2 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.FirstActivity");
//                startActivity(intent2);
//                break;
//            case R.id.button3:
//                /*????????????*/
//                Intent intent3 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.VideoComPressorActivity");
////                Intent intent3 = new Intent(this, VideoComPressorActivity.class);
//                startActivity(intent3);
//                break;
//            case R.id.button4:
//                /*shadow?????????????????????*/
//                Intent intent4 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShadowMainActivity");
////                Intent intent4 = new Intent(this, ShadowMainActivity.class);
//                startActivity(intent4);
//                break;
//            case R.id.button5:
//                /*?????????????????????*/
//                Intent intent5 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ZhiwenActtivity");
////                Intent intent5 = new Intent(this, ZhiwenActtivity.class);
//                startActivity(intent5);
//                break;
//            case R.id.button6:
//                /*???????????????*/
//                Intent intent6 = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.PictureCompressorActivity");
////                Intent intent6 = new Intent(MainActivity.this, PictureCompressorActivity.class);
//                startActivity(intent6);
//                break;
//            case R.id.button7:
//                /*???????????????textview*/
////                Intent intent7 = new Intent(this, SuperTextviewActivity.class);
////                startActivity(intent7);
//                break;
//            case R.id.button8:
//                /*Android??????+1??????*/
////                Intent intent8 = new Intent(this, GoodViewActivity.class);
////                startActivity(intent8);
//                break;
//            case R.id.button9:
//                /*Android?????????????????????*/
////                Intent intent9 = new Intent(this, BlastActivity.class);
////                startActivity(intent9);
//                break;
//            case R.id.button10:
//                /*??????????????????*/
////                Intent intent10 = new Intent(this, LiveActivity.class);
////                startActivity(intent10);
//            case R.id.button11:
//                /*????????????*/
//                startActivity(new Intent(getPackageName() + ".hs.act.slbapp.MainActivityfdt"));
//                break;
//            case R.id.button12:
//                /*Viewpager+Bottomsheet??????*/
//                startActivity(new Intent(getPackageName() + ".hs.act.slbapp.MainActivitySheet"));
//                break;
//            case R.id.button13:
//                /*???????????????????????????*/
//                startActivity(new Intent(getPackageName() + ".hs.act.slbapp.ProgressMainActivity"));
//                break;
//            case R.id.button14:
//                /*??????????????????
//                 * ???????????? 'com.rmtheis:tess-two:9.1.0'*/
//                startActivity(new Intent(getPackageName() + ".hs.act.slbapp.PicturecognitionTextMainActivity"));
//                break;
//            case R.id.button15:
//                /*?????????
//                 *????????????: https://github.com/jenly1314/ZXingLite/tree/androidx*/
//                startActivity(new Intent(getPackageName() + ".hs.act.slbapp.ZxingMainActivity"));
//                break;
//            case R.id.button16:
//                /*?????????????????????
//                 *????????????: https://github.com/shouzhong/EncryptionUtils*/
//                startActivity(new Intent(getPackageName() + ".hs.act.slbapp.SampleActivity"));
//                break;
//            default:
//                break;
//        }
//    }
//
//
//}