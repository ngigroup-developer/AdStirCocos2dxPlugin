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

1. cocos2d-x-3.0beta/tools/project-creator/create_project.py -n projectname -k com.aaa.com.sss -l cpp -p .

Download plugin
===================

1. cd projectname

2. git clone https://github.com/ngigroup-developer/AdStirCocos2dxPlugin.git cocos2d/plugin/plugins/adstir

Android / iOS
===================

1. vi Classes/HelloWorldScene.h

        // header
        #include "ProtocolAds.h"
        #include "PluginManager.h"

        // HelloWorld
        private:
            cocos2d::plugin::ProtocolAds* _adstir;
            cocos2d::plugin::TAdsDeveloperInfo devInfo;
            cocos2d::plugin::TAdsInfo adInfo;

2. vi Classes/HelloWorldScene.cpp

        // HelloWorld::init
        _adstir = dynamic_cast<cocos2d::plugin::ProtocolAds*>(cocos2d::plugin::PluginManager::getInstance()->loadPlugin("AdstirCocos2dx"));
        devInfo["media"] = "MEDIA-ID";
        devInfo["spot"] = "SPOT-NO";
        CCLOG("configDeveloperInfo s");
        _adstir->configDeveloperInfo(devInfo);
        CCLOG("configDeveloperInfo e");
        adInfo["width"] = "320";
        adInfo["height"] = "50";
        _adstir->showAds(adInfo, cocos2d::plugin::ProtocolAds::AdsPos::kPosCenter);
        
        // HelloWorld::menuCloseCallback
        _adstir->hideAds(adInfo);
        cocos2d::plugin::PluginManager::getInstance()->unloadPlugin("AdstirCocos2dx");
        _adstir = NULL;

Android only
===================

1. cp /........../AdStirSdkAndroid/v1.3.1/adstirwebview.jar cocos2d/plugin/plugins/adstir/proj.android/sdk/

2. vi cocos2d/plugin/tools/config.sh  
   Add "adstir" to ALL_PLUGINS

3. cocos2d/plugin/tools/publish.sh

4. cocos2d/plugin/tools/gameDevGuide.sh

5. cd proj.android/

6. vi jni/Android.mk

        $(call import-module,plugin/publish/protocols/android)

7. vi jni/hellocpp/main.cpp

        // header
        #include "PluginJniHelper.h"
        #include "PluginUtils.h"
        
        // cocos_android_app_init
        PluginJniHelper::setJavaVM(app->activity->vm);
        PluginJniHelper::setClassLoaderFrom(app->activity->clazz);
        cocos2d::plugin::PluginUtils::initPluginWrapper(app);

8. mkdir -p libs

9. cp ../cocos2d/plugin/publish/protocols/android/*.jar libs

10. cp ../cocos2d/plugin/publish/plugins/adstir/android/*.jar libs

11. ./build_native.py

12. android update project -p . -t android-19

13. android update project -p ../cocos2d/cocos/2d/platform/android/java/ -t android-19

14. ant debug

15. ant installd


iOS only
===================

1. cp /........../AdStirSdkiOS/1.3.0/* cocos2d/plugin/plugins/adstir/proj.ios/adstir/

2. open proj.ios_mac/projectname.xcodeproj
   1. Add "cocos2d/plugin/protocols/proj.ios/PluginProtocol.xcodeproj" to Project
   2. Add "cocos2d/plugin/plugins/adstir/proj.ios/AdstirCocos2dx.xcodeproj" to Project
   3. Add "libAdstirCocos2dx.a" to "Link Binary With Libraries"
   4. Add "libPluginProtocol.a" to "Link Binary With Libraries"
   5. Add "AdSupport.framework" to "Link Binary With Libraries"
   6. Add "../cocos2d/plugin/protocols/include" to "User Header Search Paths"
   7. Add "-ObjC" to "Other Linker Flags"

3. Run app

