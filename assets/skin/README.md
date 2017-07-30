# Create skin from libGDX skin-composer
LibGDX has its own skin composer. This tool unfortunately gives us faulty files we have to correct manually:

## JSON-file
The json file has an entry having `TextTooltip$TextTooltipStyle` in its qualifier. Remove this entry (our version of libGDX doesn't have the `TextTooltip` anymore.

## atlas-file
Add the following two lines below every `size` attribute for the following list of types:
* down
* over
* up

And add these two lines:
```
  split: 18, 18, 18, 18
  pad: 16, 16, 16, 16
```

This will convert this
```
down
  rotate: false
  xy: 1, 81
  size: 135, 55
  orig: 135, 55
  offset: 0, 0
  index: -1
```
into this
```
down
  rotate: false
  xy: 1, 81
  size: 135, 55
  split: 18, 18, 18, 18
  pad: 16, 16, 16, 16
  orig: 135, 55
  offset: 0, 0
  index: -1
```
