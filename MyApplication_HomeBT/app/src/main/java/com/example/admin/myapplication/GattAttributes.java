/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.admin.myapplication;

import java.util.UUID;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class GattAttributes {
    public static UUID BLE_WRITE_R_GET_RANDOM_VALUE = UUID.fromString("19B10002-E8F2-537E-4F6C-D104768A1214");
    public static UUID BLE_NOTIFY_RANDOM_VALUE = UUID.fromString("19B10001-E8F2-537E-4F6C-D104768A1214");
    public static UUID BLE_GLUCOSE_METER_SERVICE = UUID.fromString("19B10000-E8F2-537E-4F6C-D104768A1214");
    public static UUID NOTIFY_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
}
