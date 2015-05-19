Required environment
===================
1. Android (Mac)
    * Command Line Tools for Xcode
    * gawk
    * Android SDK
    * Android NDK
    * Apache Ant
2. Android (Linux / Cygwin)
    * Bash
    * Python
    * gawk
    * Android SDK
    * Android NDK
    * Apache Ant
3. iOS (Mac only)
    * Xcode

Make project
===================

1. cocos new ProjectName -p jp.united.AdstirTest -l cpp -d ProjectPath

Download plugin
===================

1. cd ProjectPath/ProjectName

2. git clone https://github.com/ngigroup-developer/AdStirCocos2dxPlugin.git cocos2d/plugin/plugins/adstir

Android / iOS
===================

1. Classes/HelloWorldScene.h

        // header
        #include "ProtocolAds.h"
        #include "PluginManager.h"

        // HelloWorld
        private:
            cocos2d::plugin::ProtocolAds* _adstir;
            cocos2d::plugin::TAdsDeveloperInfo devInfo;
            cocos2d::plugin::TAdsInfo adInfo;

2. Classes/HelloWorldScene.cpp

        // HelloWorld::init
        _adstir = (cocos2d::plugin::ProtocolAds*) cocos2d::plugin::PluginManager::getInstance()->loadPlugin("AdstirCocos2dx");
        devInfo["media"] = "MEDIA-ID";
        devInfo["spot"] = "SPOT-NO";
        _adstir->configDeveloperInfo(devInfo);
        adInfo["width"] = "320";
        adInfo["height"] = "50";
        _adstir->showAds(adInfo, cocos2d::plugin::ProtocolAds::AdsPos::kPosCenter);
        
        // HelloWorld::menuCloseCallback
        _adstir->hideAds(adInfo);
        cocos2d::plugin::PluginManager::getInstance()->unloadPlugin("AdstirCocos2dx");
        _adstir = NULL;

Android only
===================

1. cp /........../adstirwebview.jar cocos2d/plugin/plugins/adstir/proj.android/libs/adstirwebview.jar

2. cp /........../adstirwebview.jar cocos2d/plugin/plugins/adstir/proj.android/sdk/adstirwebview.jar

3. cocos2d/plugin/tools/publish.sh

4. cocos2d/plugin/tools/gameDevGuide.sh
    1. input /......./ProjectPath/ProjectName/proj.android

5. proj.android/jni/Android.mk

        $(call import-module,***plugin/publish***/protocols/android)

6. proj.android/jni/hellocpp/main.cpp

        // header
        #include "PluginJniHelper.h"
        
        // cocos_android_app_init
        JavaVM* vm;
        env->GetJavaVM(&vm);
        PluginJniHelper::setJavaVM(vm);

7. proj.android/project.properties

        android.library.reference.2=../cocos2d/plugin/protocols/proj.android
        android.library.reference.3=../cocos2d/plugin/plugins/adstir/proj.android

8. cocos run -p android


iOS only
===================

1. cp -r /........../AdstirAds.framework cocos2d/plugin/plugins/adstir/proj.ios/adstir/

2. open proj.ios_mac/projectname.xcodeproj
   1. Add "AdstirAds.framework" to Project
   2. Add "cocos2d/plugin/protocols/proj.ios/PluginProtocol.xcodeproj" to Project
   3. Add "cocos2d/plugin/plugins/adstir/proj.ios/AdstirCocos2dx.xcodeproj" to Project
   4. Add "AdstirAds.framework" to "Link Binary With Libraries"
   5. Add "libAdstirCocos2dx.a" to "Link Binary With Libraries"
   6. Add "libPluginProtocol.a" to "Link Binary With Libraries"
   7. Add "AdSupport.framework" to "Link Binary With Libraries"
   7. Add "MediaPlayer.framework" to "Link Binary With Libraries"
   7. Add "GameController.framework" to "Link Binary With Libraries"
   8. Add "../cocos2d/plugin/protocols/include" to "User Header Search Paths"
   9. Add "-ObjC" to "Other Linker Flags"

3. Run app


