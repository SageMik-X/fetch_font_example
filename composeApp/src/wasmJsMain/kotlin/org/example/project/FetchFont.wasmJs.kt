package org.example.project

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import kotlinx.browser.window
import kotlinx.coroutines.await
import org.jetbrains.compose.resources.MissingResourceException
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.w3c.fetch.Response
import org.w3c.files.Blob
import kotlin.js.Promise
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator

actual suspend fun fetchFont(path: String, weight: FontWeight, style: FontStyle): Font {
    return Font(path, read(path), weight, style)
}

@JsFun(
    """ (src, size, dstAddr) => {
        const mem8 = new Int8Array(wasmExports.memory.buffer, dstAddr, size);
        mem8.set(src);
    }
"""
)
private external fun jsExportInt8ArrayToWasm(src: Int8Array, size: Int, dstAddr: Int)

@JsFun("(blob) => blob.arrayBuffer()")
private external fun jsExportBlobAsArrayBuffer(blob: Blob): Promise<ArrayBuffer>

internal var getResourcePath: (path: String) -> String = { "./$it" }

suspend fun read(path: String): ByteArray {
    return readAsBlob(path).asByteArray()
}

private suspend fun readAsBlob(path: String): Blob {
    val resPath = getResourcePath(path)
    val response = window.fetch(resPath).await<Response>()
    if (!response.ok) {
        throw MissingResourceException(resPath)
    }
    return response.blob().await()
}

private suspend fun Blob.asByteArray(): ByteArray {
    val buffer: ArrayBuffer = jsExportBlobAsArrayBuffer(this).await()
    return Int8Array(buffer).asByteArray()
}

private fun Int8Array.asByteArray(): ByteArray {
    val array = this
    val size = array.length

    @OptIn(UnsafeWasmMemoryApi::class) return withScopedMemoryAllocator { allocator ->
        val memBuffer = allocator.allocate(size)
        val dstAddress = memBuffer.address.toInt()
        jsExportInt8ArrayToWasm(array, size, dstAddress)
        ByteArray(size) { i -> (memBuffer + i).loadByte() }
    }
}