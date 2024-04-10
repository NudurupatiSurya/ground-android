/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.ground.ui.datacollection.tasks.text

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import com.google.android.ground.model.submission.TextTaskData.Companion.fromString
import com.google.android.ground.ui.datacollection.tasks.AbstractTaskViewModel
import javax.inject.Inject

class TextTaskViewModel @Inject constructor(resources: Resources) :
  AbstractTaskViewModel(resources) {

  val textWatcher =
    object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        // Do nothing.
      }

      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        setValue(fromString(s.toString()))
      }

      override fun afterTextChanged(s: Editable) {
        // Do nothing.
      }
    }
}
