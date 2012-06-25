@echo off

jarsigner -keystore C:\NetBeansProject\FingerHealth\FingerApplet\keystore -storepass fhfaks -keypass fhfakp dist\FingerApplet.jar fingerapplet 
jarsigner -keystore C:\NetBeansProject\FingerHealth\FingerApplet\keystore -storepass fhfaks -keypass fhfakp dist\lib\FingerModule.jar fingerapplet 
jarsigner -keystore C:\NetBeansProject\FingerHealth\FingerApplet\keystore -storepass fhfaks -keypass fhfakp dist\lib\jssc.jar fingerapplet 