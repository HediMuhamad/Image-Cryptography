### How to use

Usage:
<br/>
`java ImageCryptography <operation> <key> <file>`

- `operation`: `enc`/`dec`
- `key`: 32bit integer"
- `file`: Path to file"

<br/>
<hr/>

### Algorithm

We used a simple algorithm to encryption/decryption operation

- get a byte of the image data
- to `XOR` with the key
- replace original byte with the result byte
- repeat until reaching the final byte.
