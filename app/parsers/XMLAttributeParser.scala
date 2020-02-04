package parsers

import scala.xml.NodeSeq


class XMLAttributeParser(body: NodeSeq) {

  def get(key: String): Option[String] = {
    (body \\ key).headOption.map { value =>
      value.text.trim
    }
  }

}
