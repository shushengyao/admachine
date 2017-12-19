import sys
from PIL import Image

file_full_path = sys.argv[1]
image = Image.open(file_full_path)
if image is not None:
    image.save(file_full_path, quality=70)
