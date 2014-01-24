Android
===================

* cocos2d-x-3.0beta/tools/project-creator/create_project.py -n mygameee -k com.aaa.com.sss -l cpp -p .
* cd mygameee
* git clone https://github.com/ngigroup-developer/AdStirCocos2dxPlugin.git cocos2d/plugin/plugins/adstir
* cp /........../AdStirSdkiOS/1.3.0/* cocos2d/plugin/plugins/adstir/proj.ios/adstir/
* cp /........../AdStirSdkAndroid/v1.3.1/adstirwebview.jar cocos2d/plugin/plugins/adstir/proj.android/sdk/
* vi cocos2d/plugin/tools/config.sh
* cocos2d/plugin/tools/publish.sh

* cocos2d/plugin/tools/gameDevGuide.sh

* vi Classes/HelloWorldScene.h
edit code

    //header
    #include "ProtocolAds.h"
    #include "PluginManager.h"

    //class HelloWorld
    private:
        cocos2d::plugin::ProtocolAds* _adstir;
        cocos2d::plugin::TAdsDeveloperInfo devInfo;
        cocos2d::plugin::TAdsInfo adInfo;


vi Classes/HelloWorldScene.cpp
###
#include "PluginJniHelper.h"
jint JNI_OnLoad( JavaVM* vm, void* reserved )
{
	cocos2d::PluginJniHelper::setJavaVM(vm); // for plugins
	return JNI_VERSION_1_4;
}
###
    _adstir = dynamic_cast<cocos2d::plugin::ProtocolAds*>(cocos2d::plugin::PluginManager::getInstance()->loadPlugin("AdstirCocos2dx"));
    devInfo["media"] = "MEDIA-228eaf21";
    devInfo["spot"] = "9";
    CCLOG("configDeveloperInfo s");
    _adstir->configDeveloperInfo(devInfo);
    CCLOG("configDeveloperInfo e");
    adInfo["width"] = "320";
    adInfo["height"] = "50";
    _adstir->showAds(adInfo, cocos2d::plugin::ProtocolAds::AdsPos::kPosCenter);
###
    _adstir->hideAds(adInfo);
    cocos2d::plugin::PluginManager::getInstance()->unloadPlugin("AdstirCocos2dx");
    _adstir = NULL;
###


cd proj.android/

vi jni/Android.mk
###
$(call import-add-path,/home/reiji-terasaka/git/cocos/mygameee/cocos2d/plugin/publish)
###

cp ../cocos2d/plugin/publish/protocols/android/*.jar libs
cp ../cocos2d/plugin/publish/plugins/adstir/android/* libs

./build_native.py
android update project -p . -t android-17
android update project -p ../cocos2d/cocos/2d/platform/android/java/ -t android-17
ant debug
ant installd









