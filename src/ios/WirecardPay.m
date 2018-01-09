/********* WirecardPay.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

#import "Accept.h"
#import "AcceptBasket.h"
#import "AcceptBasketItem.h"
#import "AcceptCustomDataResponse.h"
#import "AcceptDataService.h"
#import "AcceptDataServiceConfig.h"
#import "AcceptExtensionPrinterInfo.h"
#import "AcceptExtensionTerminalInfo.h"
#import "AcceptPaymentParameters.h"
#import "AcceptPrinterExtension.h"
#import "AcceptReceipt.h"
#import "AcceptStatistics.h"
#import "AcceptTerminalConfig.h"
#import "AcceptTransaction.h"
#import "AcceptTransactionsQuery.h"
#import "AcceptUserResponse.h"
#import "AcceptUtils.h"
#import "AcceptV3DataTypes.h"
#import "DatecsAcceptExtension.h"
#import "DatecsAcceptPrinterExtension.h"
#import "EmvSwipeAcceptExtension.h"
#import "MPOPAcceptExtension.h"
#import "mPOPCommunication.h"
#import "mPOPPrinterFunctions.h"
#import "PosMateExtension.h"
#import "UnimagAcceptExtension.h"
￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼￼

@interface WirecardPay : CDVPlugin {
  // Member variables go here.
  
}

- (void)coolMethod:(CDVInvokedUrlCommand*)command;
- (void)doLogin:(CDVInvokedUrlCommand*)command;
@end

@implementation WirecardPay 

- (void)coolMethod:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* echo = [command.arguments objectAtIndex:0];

    if (echo != nil && [echo length] > 0) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)testAndroid:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* echo = [command.arguments objectAtIndex:0];

    if (echo != nil && [echo length] > 0) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
