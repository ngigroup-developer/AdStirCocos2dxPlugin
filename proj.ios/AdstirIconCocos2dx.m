/****************************************************************************
 Copyright (c) 2014 UNITED,inc.
 Copyright (c) 2013 cocos2d-x.org
 
 http://www.cocos2d-x.org
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 ****************************************************************************/

#import <Foundation/Foundation.h>
#import "InterfaceAds.h"
#import <AdstirAds/AdstirAds.h>
#import "AdsWrapper.h"

#define LOG(...) NSLog(__VA_ARGS__);


@interface AdstirIconCocos2dx : NSObject <InterfaceAds>
{
}

@property BOOL debug;
@property (copy, nonatomic) NSString* media;
@property (assign, nonatomic) int spot;
@property (retain, nonatomic) AdstirIconView* adstir;

/**
 interfaces from InterfaceAds
 */
- (void) configDeveloperInfo: (NSMutableDictionary*) devInfo;
- (void) showAds: (NSMutableDictionary*) info position:(int) pos;
- (void) hideAds: (NSMutableDictionary*) info;
- (void) queryPoints;
- (void) spendPoints: (int) points;
- (void) setDebugMode: (BOOL) isDebugMode;
- (NSString*) getSDKVersion;
- (NSString*) getPluginVersion;

@end


@implementation AdstirIconCocos2dx

- (void) dealloc
{
    self.adstir = nil;
    self.media = nil;
    self.spot = 0;
    [super dealloc];
}

#pragma mark InterfaceAds impl

- (void) configDeveloperInfo: (NSMutableDictionary*) devInfo
{
    self.media = (NSString*) [devInfo objectForKey:@"media"];
    self.spot = [(NSString*) [devInfo objectForKey:@"spot"] intValue];
}

- (void) showAds: (NSMutableDictionary*)info position:(int) pos
{
    if ([info objectForKey:@"width"] == nil){
        LOG(@"showAds() not correctly invoked in AdstirCocos2dx!");
        return;
    }
    if ([info objectForKey:@"height"] == nil){
        LOG(@"showAds() not correctly invoked in AdstirCocos2dx!");
        return;
    }
    if ([info objectForKey:@"slot"] == nil){
        LOG(@"showAds() not correctly invoked in AdstirCocos2dx!");
        return;
    }
    if ([info objectForKey:@"center"] == nil){
        LOG(@"showAds() not correctly invoked in AdstirCocos2dx!");
        return;
    }
    int width = [[info objectForKey:@"width"]intValue];
    int height = [[info objectForKey:@"height"]intValue];
    int slot = [[info objectForKey:@"slot"]intValue];
    bool center = [[info objectForKey:@"center"] boolValue];
    [self showBanner:pos width:width height:height slot:slot center:center];
}

- (void) hideAds: (NSMutableDictionary*) info
{
    if (self.adstir != nil) {
        [self.adstir removeFromSuperview];
        self.adstir = nil;
    }
}

- (void) queryPoints
{
    LOG(@"AdstirCocos2dx not support queryPoints");
}

- (void) spendPoints: (int) points
{
    LOG(@"AdstirCocos2dx not support spendPoints");
}

- (void) setDebugMode: (BOOL) isDebugMode
{
    LOG(@"AdstirCocos2dx not support isDebugMode");
}

- (NSString*) getSDKVersion
{
    LOG(@"AdstirCocos2dx not support getSDKVersion");
    return @"AdstirCocos2dx not support getSDKVersion";
}

- (NSString*) getPluginVersion
{
    LOG(@"AdstirCocos2dx not support getPluginVersion");
    return @"AdstirCocos2dx not support getPluginVersion";
}

- (void) showBanner:(int)pos width:(int)width height:(int)height slot:(int)slot center:(bool)center
{
    if (self.media == nil || self.spot == 0) {
        LOG(@"configDeveloperInfo() not correctly invoked in AdstirCocos2dx!");
        return;
    }
    if (width == 0 || height == 0) {
        LOG(@"showAds() not correctly invoked in AdstirCocos2dx!");
        return;
    }
    if (self.adstir != nil) {
        [self.adstir removeFromSuperview];
        self.adstir = nil;
    }
    
    self.adstir = [[[AdstirIconView alloc] initWithFrame:CGRectMake(0,0,width,height)]autorelease];
    self.adstir.media = self.media;
    self.adstir.spot = self.spot;
    self.adstir.slot = slot;
    self.adstir.isCenter = center;
    [AdsWrapper addAdView:self.adstir atPos:pos];
    [AdsWrapper onAdsResult:self withRet:kAdsReceived withMsg:@"Ads request received success!"];
}

@end
