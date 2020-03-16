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

package com.google.android.gnd.ui.editobservation;

import android.net.Uri;
import android.view.View;
import androidx.databinding.ObservableMap;
import androidx.databinding.ObservableMap.OnMapChangedCallback;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gnd.model.form.Field;
import com.google.android.gnd.model.observation.Response;
import com.google.android.gnd.system.StorageManager;
import com.google.android.gnd.ui.common.AbstractViewModel;
import javax.inject.Inject;

public class PhotoFieldViewModel extends AbstractViewModel {

  private final StorageManager storageManager;
  private final MutableLiveData<Uri> destinationPath = new MutableLiveData<>();
  private final MutableLiveData<Integer> photoPreviewVisibility = new MutableLiveData<>(View.GONE);

  @Inject
  PhotoFieldViewModel(StorageManager storageManager) {
    this.storageManager = storageManager;
  }

  public LiveData<Uri> getDestinationPath() {
    return destinationPath;
  }

  public MutableLiveData<Integer> photoPreviewVisibility() {
    return photoPreviewVisibility;
  }

  public void init(Field field, ObservableMap<String, Response> responses) {
    // Load last saved value
    update(responses.get(field.getId()), field);

    // Observe response updates
    responses.addOnMapChangedCallback(
        new OnMapChangedCallback<ObservableMap<String, Response>, String, Response>() {
          @Override
          public void onMapChanged(ObservableMap<String, Response> sender, String key) {
            if (key.equals(field.getId())) {
              update(sender.get(key), field);
            }
          }
        });
  }

  private void update(Response response, Field field) {
    if (response == null) {
      photoPreviewVisibility.setValue(View.GONE);
    } else {
      String value = response.getDetailsText(field);
      if (value.isEmpty()) {
        photoPreviewVisibility.setValue(View.GONE);
      } else {
        photoPreviewVisibility.setValue(View.VISIBLE);
        disposeOnClear(
            storageManager.loadUriFromDestinationPath(value).subscribe(destinationPath::setValue));
      }
    }
  }
}
