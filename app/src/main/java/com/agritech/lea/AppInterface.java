package com.agritech.lea;

public interface AppInterface {

    String login = "http://leaug.herokuapp.com/api/login?phone=";

    String news = "http://leaug.herokuapp.com/api/news";
    String suppliers = "http://leaug.herokuapp.com/api/suppliers";
    String tracker = "http://leaug.herokuapp.com/api/tracker?date=";

    // Gallery directory name to store the images
    String GALLERY_DIRECTORY_NAME = "Lea";

    int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    String KEY_IMAGE_STORAGE_PATH = "image_path";

    int MEDIA_TYPE_IMAGE = 1;
    int MEDIA_TYPE_VIDEO = 2;

    // Bitmap sampling size
    int BITMAP_SAMPLE_SIZE = 8;

    // Image and Video file extensions
    String IMAGE_EXTENSION = "jpg";
    String VIDEO_EXTENSION = "mp4";

}
