const fs = require("fs");
const path = require("path");

const outDir = path.join(
  "D:/Projects/Android/3DModel/app/src/main/assets/models"
);

fs.mkdirSync(outDir, { recursive: true });

function v(x, y, z) {
  return [x, y, z];
}

function tri(list, a, b, c) {
  list.push({ a, b, c });
}

function cube() {
  const t = [];
  const p = 0.6;
  const n = -p;
  const faces = [
    [v(n, n, p), v(p, n, p), v(p, p, p), v(n, p, p)],
    [v(p, n, n), v(n, n, n), v(n, p, n), v(p, p, n)],
    [v(n, n, n), v(n, n, p), v(n, p, p), v(n, p, n)],
    [v(p, n, p), v(p, n, n), v(p, p, n), v(p, p, p)],
    [v(n, p, p), v(p, p, p), v(p, p, n), v(n, p, n)],
    [v(n, n, n), v(p, n, n), v(p, n, p), v(n, n, p)]
  ];
  const faceColors = [
    [0.90, 0.93, 0.98, 0.30],
    [0.68, 0.76, 0.86, 0.28],
    [0.56, 0.68, 0.92, 0.34],
    [0.78, 0.56, 0.88, 0.30],
    [0.56, 0.86, 0.76, 0.32],
    [0.88, 0.72, 0.56, 0.28]
  ];
  faces.forEach((face, i) => {
    tri(t, face[0], face[1], face[2]);
    tri(t, face[0], face[2], face[3]);
    t[t.length - 2].color = faceColors[i];
    t[t.length - 1].color = faceColors[i];
  });
  return t;
}

function pyramid() {
  const t = [];
  const b = 0.7;
  const h = 0.8;
  const top = v(0, h, 0);
  const a = v(-b, 0, -b);
  const c = v(b, 0, -b);
  const d = v(b, 0, b);
  const e = v(-b, 0, b);
  tri(t, a, c, d);
  tri(t, a, d, e);
  tri(t, a, c, top);
  tri(t, c, d, top);
  tri(t, d, e, top);
  tri(t, e, a, top);
  t[0].color = [0.82, 0.72, 0.46, 0.32];
  t[1].color = [0.84, 0.60, 0.28, 0.28];
  t[2].color = [0.92, 0.82, 0.58, 0.30];
  t[3].color = [0.86, 0.64, 0.26, 0.28];
  t[4].color = [0.72, 0.52, 0.20, 0.26];
  t[5].color = [0.88, 0.74, 0.34, 0.30];
  return t;
}

function tetra() {
  const t = [];
  const a = v(0, 0.9, 0);
  const b = v(-0.7, -0.5, 0.7);
  const c = v(0.7, -0.5, 0.7);
  const d = v(0, -0.5, -0.8);
  tri(t, a, b, c);
  tri(t, a, c, d);
  tri(t, a, d, b);
  tri(t, b, d, c);
  t[0].color = [0.74, 0.90, 0.82, 0.32];
  t[1].color = [0.48, 0.78, 0.64, 0.28];
  t[2].color = [0.66, 0.86, 0.56, 0.30];
  t[3].color = [0.38, 0.64, 0.52, 0.24];
  return t;
}

function octa() {
  const t = [];
  const top = v(0, 1, 0);
  const bottom = v(0, -1, 0);
  const left = v(-0.8, 0, 0);
  const right = v(0.8, 0, 0);
  const front = v(0, 0, 0.85);
  const back = v(0, 0, -0.85);
  tri(t, top, front, right);
  tri(t, top, right, back);
  tri(t, top, back, left);
  tri(t, top, left, front);
  tri(t, bottom, right, front);
  tri(t, bottom, back, right);
  tri(t, bottom, left, back);
  tri(t, bottom, front, left);
  t[0].color = [0.70, 0.84, 0.92, 0.32];
  t[1].color = [0.46, 0.66, 0.84, 0.26];
  t[2].color = [0.60, 0.76, 0.94, 0.34];
  t[3].color = [0.34, 0.52, 0.72, 0.22];
  t[4].color = [0.86, 0.90, 0.94, 0.36];
  t[5].color = [0.58, 0.70, 0.82, 0.28];
  t[6].color = [0.42, 0.58, 0.76, 0.24];
  t[7].color = [0.52, 0.68, 0.86, 0.30];
  return t;
}

function prism() {
  const t = [];
  const topY = 0.65;
  const botY = -0.65;
  const a = v(-0.8, botY, -0.5);
  const b = v(0.8, botY, -0.5);
  const c = v(0, botY, 0.9);
  const a2 = v(-0.8, topY, -0.5);
  const b2 = v(0.8, topY, -0.5);
  const c2 = v(0, topY, 0.9);
  tri(t, a, b, c);
  tri(t, a2, c2, b2);
  tri(t, a, a2, b2);
  tri(t, a, b2, b);
  tri(t, b, b2, c2);
  tri(t, b, c2, c);
  tri(t, c, c2, a2);
  tri(t, c, a2, a);
  t[0].color = [0.90, 0.70, 0.58, 0.32];
  t[1].color = [0.82, 0.50, 0.40, 0.26];
  t[2].color = [0.66, 0.38, 0.34, 0.24];
  t[3].color = [0.88, 0.58, 0.48, 0.30];
  t[4].color = [0.76, 0.46, 0.38, 0.26];
  t[5].color = [0.60, 0.34, 0.30, 0.22];
  t[6].color = [0.92, 0.78, 0.72, 0.34];
  t[7].color = [0.84, 0.64, 0.58, 0.28];
  return t;
}

function writeGlb(name, tris, color) {
  const positions = [];
  const colors = [];
  const indices = [];
  let idx = 0;

  for (const face of tris) {
    positions.push(...face.a, ...face.b, ...face.c);
    const faceColor = face.color || color;
    colors.push(...faceColor, ...faceColor, ...faceColor);
    indices.push(idx, idx + 1, idx + 2);
    idx += 3;
  }

  const posBuf = Buffer.alloc(positions.length * 4);
  positions.forEach((value, i) => posBuf.writeFloatLE(value, i * 4));

  const colorBuf = Buffer.alloc(colors.length * 4);
  colors.forEach((value, i) => colorBuf.writeFloatLE(value, i * 4));

  const idxBuf = Buffer.alloc(indices.length * 2);
  indices.forEach((value, i) => idxBuf.writeUInt16LE(value, i * 2));

  const json = {
    asset: { version: "2.0", generator: "codex-procedural-glb" },
    extensionsUsed: ["KHR_materials_unlit"],
    buffers: [{ byteLength: posBuf.length + colorBuf.length + idxBuf.length }],
    bufferViews: [
      { buffer: 0, byteOffset: 0, byteLength: posBuf.length, target: 34962 },
      {
        buffer: 0,
        byteOffset: posBuf.length,
        byteLength: colorBuf.length,
        target: 34962
      },
      {
        buffer: 0,
        byteOffset: posBuf.length + colorBuf.length,
        byteLength: idxBuf.length,
        target: 34963
      }
    ],
    accessors: [
      {
        bufferView: 0,
        componentType: 5126,
        count: positions.length / 3,
        type: "VEC3",
        min: [-1, -1, -1],
        max: [1, 1, 1]
      },
      {
        bufferView: 1,
        componentType: 5126,
        count: colors.length / 4,
        type: "VEC4",
        min: [0, 0, 0, 1],
        max: [1, 1, 1, 1]
      },
      {
        bufferView: 2,
        componentType: 5123,
        count: indices.length,
        type: "SCALAR"
      }
    ],
    materials: [
      {
        doubleSided: true,
        alphaMode: "BLEND",
        pbrMetallicRoughness: {
          baseColorFactor: [0.92, 0.92, 0.92, 0.30],
          metallicFactor: 0.0,
          roughnessFactor: 1.0
        },
        extensions: { KHR_materials_unlit: {} }
      }
    ],
    meshes: [
      {
        primitives: [
          { attributes: { POSITION: 0, COLOR_0: 1 }, indices: 2, material: 0 }
        ]
      }
    ],
    nodes: [{ mesh: 0 }],
    scenes: [{ nodes: [0] }],
    scene: 0
  };

  const jsonBuf = Buffer.from(JSON.stringify(json), "utf8");
  const jsonPad = (4 - (jsonBuf.length % 4)) % 4;
  const binPad = (4 - (idxBuf.length % 4)) % 4;
  const colorPad = (4 - (colorBuf.length % 4)) % 4;
  const jsonChunk = Buffer.concat([jsonBuf, Buffer.alloc(jsonPad, 0x20)]);
  const binChunk = Buffer.concat([
    posBuf,
    colorBuf,
    idxBuf,
    Buffer.alloc(colorPad + binPad)
  ]);

  const totalLength = 12 + 8 + jsonChunk.length + 8 + binChunk.length;
  const header = Buffer.alloc(12);
  header.writeUInt32LE(0x46546c67, 0);
  header.writeUInt32LE(2, 4);
  header.writeUInt32LE(totalLength, 8);

  const jsonHeader = Buffer.alloc(8);
  jsonHeader.writeUInt32LE(jsonChunk.length, 0);
  jsonHeader.write("JSON", 4, 4, "ascii");

  const binHeader = Buffer.alloc(8);
  binHeader.writeUInt32LE(binChunk.length, 0);
  binHeader.write("BIN\0", 4, 4, "ascii");

  fs.writeFileSync(
    path.join(outDir, `${name}.glb`),
    Buffer.concat([header, jsonHeader, jsonChunk, binHeader, binChunk])
  );
}

writeGlb("cube_blue", cube(), [0.36, 0.64, 1.0, 1.0]);
writeGlb("pyramid_amber", pyramid(), [0.94, 0.71, 0.28, 1.0]);
writeGlb("tetra_mint", tetra(), [0.44, 0.84, 0.7, 1.0]);
writeGlb("octa_sky", octa(), [0.46, 0.74, 1.0, 1.0]);
writeGlb("prism_coral", prism(), [0.95, 0.53, 0.45, 1.0]);
