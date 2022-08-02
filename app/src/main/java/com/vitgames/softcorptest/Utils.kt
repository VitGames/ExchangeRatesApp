package com.vitgames.softcorptest

import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun Subscription.addToComposite(compositeSubscription: CompositeSubscription) = compositeSubscription.add(this)