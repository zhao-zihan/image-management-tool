# Image Management Tool

## Student Info

- course: INFO 5100
- name: Zihan Zhao
- NUID: 001265988



## Class Diagram

![image-20220418192247356](images/image-20220418192247356.png)



## Tests

### 1、source image

- image name: s_201052592911
- image format: .jpg

![image-20220418192359910](images/image-20220418192359910.png)



### 2、GUI design

- left side is designed for displaying image thumbnails
- middle side is designed for displaying image info
- right side is designed for image operations

![image-20220418192533695](images/image-20220418192533695.png)



### 3、upload image

- Click on the "Upload from my computer" button, then select and upload the image

![image-20220418192740301](images/image-20220418192740301.png)



- After uploading, the thumbnail and some basic information of the image will be displayed on GUI

![image-20220418192933417](images/image-20220418192933417.png)



- Since many photos hide their metadate including location and date. I used an outside library called metadata-extractor to get all the hidden data and print it to the console

![image-20220418193149703](images/image-20220418193149703.png)



### 4、convert image

- First, users needs to select the output they want, and only after they have uploaded the image and selected the desired output format, then the convert button would be enabled.

![image-20220418193355682](images/image-20220418193355682.png)



- The javaxt package only accepts the following formats for input and output

![image-20220418193506167](images/image-20220418193506167.png)

- After selection, the "selectOuputLabel" will display the format that users selected, then users can hit the convert button, and the software will memorize the desired output in the computer memory

![image-20220418193737801](images/image-20220418193737801.png)



### 5、download converted image

- First, same as uploading, users need to push the "Select a directory to save" button to select a directory in their computer to save the converted image

![image-20220418193931315](images/image-20220418193931315.png)



- After the selection, the "Select a directory to save" button will change to the directory that users just selected

![image-20220418194032414](images/image-20220418194032414.png)



- The last step is to hit the download button and the image will be saved
- The following image is converted and downloaded using this image management tool
- The output format and location are correct

![image-20220418194351468](images/image-20220418194351468.png)



### 6、restart and continue the process

- A "restart" button is designed to allow users to continue to upload other images

![image-20220418194639494](images/image-20220418194639494.png)



- What this button does is resetting every variables and labels to default values

 ![image-20220418194739557](images/image-20220418194739557.png)



### Hope this file explains well about the design and functions of the software, thank you for time reading this!