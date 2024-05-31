const puppeteer = require('puppeteer');
const fs = require('fs');
const path = require('path');

(async () => {
    const browser = await puppeteer.launch();
    const page = await browser.newPage();
    const filePath = `file://${path.resolve(__dirname, 'src/main/resources/templates/chart.html')}`;
    await page.goto(filePath);
    const chart = await page.$('canvas');
    const imageBuffer = await chart.screenshot();

    const outputPath = path.resolve(__dirname, 'src/main/resources/static/chart.png');
    fs.writeFileSync(outputPath, imageBuffer);

    await browser.close();
})();