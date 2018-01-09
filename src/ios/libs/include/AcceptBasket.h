//
//  Accept for iOS
//
//  Created by Radoslav Danko and Francisco Fortes
//  Copyright (c) 2016 Wirecard. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AcceptTransaction.h"

/**
 *  @class AcceptBasket
 *  @discussion Basket info class
 **/
@interface AcceptBasket : NSObject
/// Transaction Unique ID
@property (nonatomic, strong) NSString * transactionUniqueId;
/// Basket Items
@property (nonatomic, readonly) NSMutableArray * items;
/// Payment note
@property (nonatomic, strong) NSString * note;
/// Latitude
@property NSNumber *lat;
/// Longitude
@property NSNumber *lng;
/// Currency code
@property (nonatomic, strong) NSString * currencyAsISO4217Code;
/// Net taxation
@property (nonatomic, strong) NSNumber * netTaxation;
/// Discount
@property (nonatomic, strong) NSDecimalNumber * discount;
/// Additional information linked to this payment
@property (nonatomic,strong) NSDictionary *customFields;
/// Information about Sub Merchant
@property (nonatomic,strong) AcceptSubMerchant *subMerchant;
/// Payment Engine Function ID
@property (nonatomic, strong) NSString * peFunctionID;
/// Payment Engine Job ID
@property (nonatomic, strong) NSString * peJobID;
/// Elastic Engine Order Number
@property (nonatomic, strong) NSString * eeOrderNumber;
/// Elastic Engine Payment Descriptor
@property (nonatomic, strong) NSString * eeDescriptor;
/**
 *  @brief Calculate the total for the basket
 *  @param handler Number handler efining decimal behaviour
 **/
- (NSDecimalNumber*) total:(NSDecimalNumberHandler*)handler;

/**
 *  @brief Add tip to the basket
 *  @param tip Tip value to be added
 **/
- (void)addTip:(NSInteger)tip;

@end
