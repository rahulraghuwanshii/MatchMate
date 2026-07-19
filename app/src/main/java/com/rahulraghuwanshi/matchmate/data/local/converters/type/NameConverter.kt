
package com.rahulraghuwanshi.matchmate.data.local.converters.type

import com.rahulraghuwanshi.matchmate.data.local.converters.GenericTypeConverter
import com.rahulraghuwanshi.matchmate.data.network.model.Dob
import com.rahulraghuwanshi.matchmate.data.network.model.Id
import com.rahulraghuwanshi.matchmate.data.network.model.Location
import com.rahulraghuwanshi.matchmate.data.network.model.Login
import com.rahulraghuwanshi.matchmate.data.network.model.Name
import com.rahulraghuwanshi.matchmate.data.network.model.Picture
import com.rahulraghuwanshi.matchmate.data.network.model.Registered

class NameConverter : GenericTypeConverter<Name>(Name.serializer())

class LocationConverter : GenericTypeConverter<Location>(Location.serializer())

class PictureConverter : GenericTypeConverter<Picture>(Picture.serializer())

class LoginConverter : GenericTypeConverter<Login>(Login.serializer())

class RegisteredConverter : GenericTypeConverter<Registered>(Registered.serializer())

class DobConverter : GenericTypeConverter<Dob>(Dob.serializer())

class IdConverter : GenericTypeConverter<Id>(Id.serializer())