# toId [![Build Status](https://travis-ci.org/CreaturePhil/toId.svg?branch=master)](https://travis-ci.org/CreaturePhil/toId)

Converts to an Id which is only lowercase alphanumeric characters.

## Install

```
$ npm install --save toid
```

## Usage

```js
var toId = require('toid');

toId('Some Username-123');
// 'someusername123'

toId('Crystal★Tempo');
// 'crystaltempo'

toId('Eden (Saiyan)');
// 'edensaiyan'

toId('Some_Mega_Man');
// 'somemegaman'

toId('BatMan');
// 'batman'
```

## License

[MIT](LICENSE)
