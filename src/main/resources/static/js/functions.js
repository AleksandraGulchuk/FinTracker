function capitalizeFirstLetter(str) {
    str = str.toLowerCase();
    const letter = str.charAt(0).toUpperCase();
    return letter + str.slice(1);
}

function convertStringToMap(summary) {
    summary = summary.replaceAll('=', '":"');
    summary = summary.replaceAll('{', '{"');
    summary = summary.replaceAll('}', '"}');
    summary = summary.replaceAll(', ', '","');

    let map = new Map(Object.entries(JSON.parse(summary)));
    return map;
}

function generatePastelColors(dataLength) {
    const colors = [];
    for (let i = 0; i < dataLength; i++) {
        const hue = (i * (360 / dataLength)) % 360;
        const saturation = 50;
        const lightness = 80;
        colors.push(`hsl(${hue}, ${saturation}%, ${lightness}%)`);
    }
    return colors;
}