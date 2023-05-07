# -resize all image to 225x330 with magick
for file in *.png; do
    magick $file -resize 225x330 $file
done