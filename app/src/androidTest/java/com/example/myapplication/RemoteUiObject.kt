package com.example.myapplication
import android.util.Log

/**
 * A recursive structure that holds a map of its child nodes so that we can refer to and call on
 * these objects remotely by taking note of IDs to refer to later (when we decide to pinch zoom on
 * a specific view that we found in a previous step, for example. These are the [accessibilityIds].
 * They will be saved on the [ResourceDataStoreReader] with everything else in WStep's memory.
 */
open class RemoteUiObject(
    val accessibilityId: String, /* the unique string we use for this view in a specific test.*/
) {
    val children = mapOf<String, RemoteUiObject>()

    /**
     * Find any UiObject (or UiObject2)
     * @param id: String that you will store on both the Android and ToD side for lookups.
     * @return true if the object was successfully stored and can be used in the future
     *
     * ONLY BY ID (String)
     * We support finding, saving, interacting with UiObject and UiObject2 with this method.
     * You must define the corresponding object on the Android side,
     * and use the unique name you passed to this method as the key in the
     * `ComplexObjects` map.
     */
    private fun findUiObject(id: String): RemoteUiObject? {
        val temp = children[id] //add(UiNode(selectorName, type))
        // children[temp] = send("findUiObject", id)
        Log.i("TAG", "replacing id: $id with ${"/*children[temp]?.accessibilityId*/}"}")
        return temp
    }
}
